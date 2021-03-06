# Tutorial - Ingesting Transactions into Aiven Kafka via Spring Kafka App

## **Spring Kafka CSV Ingest Project**
### Important Code Snippets
Since springboot framework allows us to manage our codebase much more efficiently with using annotations, throughout the code one would see presence of several of those.

The scope of this documentation is to not articulate why, what and how to use annotation but that can be discussed as part of another Springboot 1-o-1 sessions.

Below sections will highlight some important aspects into the code base and also a little bit of details.
#### Boot Application Entrypoint Class
Any Java based Springboot application is instantiated with main method as you can see in the below code.

Once the application is run then, next in the step for this application is a commandline executor which reads the CSV file and asks to process and send the transaction json message line by line. 

Once all of the CSV lines are processed, the application goes in to stand by mode for any further inputs

```aidl
@SpringBootApplication
@ComponentScan("org.aiven")
@ComponentScan("reader")
public class CSVProducerApp {

    public static void main(String[] args)
    {
        SpringApplication.run(CSVProducerApp.class,args);
    }

    @Bean
    public CommandLineRunner sendFile(LineProducer lineProducer, CsvReader csvReader)
    {
        return args -> {
            csvReader.readAndCallbackFinTxn("classpath:transactions.csv", lineProducer::sendFinTxnMessage);
        };
    }
}
```

#### ProducerFactory Bean and Kafka Cluster Connection
Some important points to note here:
1. Use of Kafka, Configuration and Component Scanning annotation
2. Choice of some configurations such as 
   
    2.1 SASL_SSL security protocol 
   
    2.2 TLS version 1.2 

    2.3 request.timeout.ms

    2.4 Key Serializer and Value Serializer

```aidl

@EnableKafka
@Configuration
@ComponentScan("org.aiven")
public class ProducerConfiguration {
    @Bean
    public ProducerFactory<String, String> producerFactory() {
        System.out.println("Working Directory = " + System.getProperty("user.dir"));

        Map<String, Object> configProps = new HashMap<>();

        configProps.put(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "bootstrapserver-url:port"); // bootstrap server URL
        configProps.put("security.protocol","SASL_SSL"); // kafka client security protocol
        configProps.put("ssl.client.auth","requested"); // Whether Kafka Producer client should authenticate with broker
        configProps.put("ssl.endpoint.identification.algorithm","https");
        configProps.put("ssl.enabled.protocols","TLSv1.2"); // SSL protocol version to be used (Min TLS version recommended is 1.2)
        configProps.put("sasl.mechanism","PLAIN"); // SASL mechanism
        configProps.put("ssl.truststore.location","/path/to/local/truststore/file"); // Location of truststore, the current keystore file format is jks
        configProps.put("ssl.truststore.password","truststore-security-password"); // password configured for truststore jks file
        configProps.put("sasl.jaas.config","org.apache.kafka.common.security.plain.PlainLoginModule required username='username-from-aiven-admin-console' password='password-from-aiven-admin-console';"); // SASL Plain Authentication credentials
        configProps.put("request.timeout.ms",20000);
        configProps.put(ProducerConfig.RECONNECT_BACKOFF_MAX_MS_CONFIG,1000);
        configProps.put(
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class); // key serializer class
        configProps.put(
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class); //value serializer class
        return new DefaultKafkaProducerFactory<>(configProps);

    }
    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        return objectMapper;
    }
}
```

#### CSV Reader Bean
The purpose of this Bean is to ensure the CSV file is read as a stream of input and also it adheres to the field mapping provided in _**io**_ package such that validations such as ordering, data type match, File IO etc are strictly maintained via code

The bean also has integration with slf4j to emit any caught exceptions via Exception handlers.

```aidl
public void readAndCallbackFinTxn(String filename, Consumer<FinancialTransactionsMessage> consume) {
        CsvBeanReader beanReader = null;
        try {
            Resource resource = resourceLoader.getResource(filename);
            beanReader = new CsvBeanReader(new InputStreamReader(resource.getInputStream()), CsvPreference.STANDARD_PREFERENCE);
            // Consume the header line
            beanReader.getHeader(true);
            final CellProcessor[] processors = Fields.getProcessors();

            FinancialTransactionsMessage message;
            while( (message = beanReader.read(FinancialTransactionsMessage.class,  Fields.getFieldMapping(), processors)) != null ) {
                consume.accept(message);
            }
            logger.info("All lines read");

        } catch (FileNotFoundException e) {
            logger.error("No fIle found", e);
        } catch (IOException e) {
            logger.error("Error while reading file: " +  filename, e);
        } finally {
            closeReader(beanReader);
        }
    }
```
#### Producer Class
LineProducer class is the code kafka producer class that instantiates KafkaTemplate and ObjectMapper from the ProducerConfiguration factory

The entry point of the application calls _sendFinTxnMessage_ function that processes the line from CSV, maps the read data points, maps it to the FinancialTransactionMessage object and then publishes on the topic financial_transaction topic.

**NOTE :** Here the assumption is that in Aiven Kafka Cluster the topic named financial_transaction exists, else it will attempt to create a topic in the process. It is a Kafka Server Side best practice to not let client application create the topics via application code, rather during the CD process align the topic creation/validation process as a deployment step first

```aidl
@Component
@ComponentScan("io")
@ConfigurationPropertiesScan("utils.ProducerConfiguration")
public class LineProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    private Logger logger = LoggerFactory.getLogger(LineProducer.class);


    public LineProducer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = new ProducerConfiguration().kafkaTemplate();
        this.objectMapper = new ProducerConfiguration().objectMapper();

    }

    public void sendFinTxnMessage(FinancialTransactionsMessage lineMessage) {
        try {
            kafkaTemplate.send("financial_transaction", String.valueOf(UUID.randomUUID()), objectMapper.writeValueAsString(lineMessage));
            logger.info("Line with id: {} sent", lineMessage.getTransaction_id());
        } catch (JsonProcessingException e) {
            logger.error("Error while serializing " + lineMessage.toString(), e);
        }
    }
}
```

##### Code Repository Structure
![alt_text](https://github.com/mpneural/aiven-takehome-demo-project/blob/master/assets/screenshots/source-code-structure.png?raw=true)

#### Code Structure Pattern
Entry Point Package - _org.aiven.entry_

Repository Pattern Package - _io_

Utility Package - _utils_

Reader Package - _reader_

Resources
1. client side certificates - ca.pem, client.truststore.jks
2. data resource - transactions.csv

##### Debug Execution Output
```aidl

```
