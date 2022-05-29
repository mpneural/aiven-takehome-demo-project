# Tutorial - Ingesting Transactions into Aiven Kafka via Spring Kafka App

## Context

Aiven is working with a travel industry grwoth stage customer towards an initial adoption of Kafka turning their Internal BI systems to unlock real-time Analytics capabilities. 

Within customer's eco-system there are 3 groups that are bootstrapping their journey on Aiven Kafka. The groups are relatively new to Kafka eco-system though the Chief Architect comes from years of Event Driven Data Systems using Kafka from the past. 

Namely the groups are : DataOps, Data Engineers, Business Intelligence. 
Their responsbility respectively are:

****DataOps** -** Operationalizing early build of Aiven Kafka and Observability

****Data Engineering** -** Building an initial Data Ingest Application for their tenant transactions data that is currently being maintained as a series of nightly CSV files. The goal is to bring this data in motion via the chosen Kafka Nervous system as the nightly transactions are rolled in CSV. 

**Business Intelligence -** Build financial analytical queries the will enable feeds into dashboard that provides insight about transactions for and across their tenants to the customer success team

## Scope
The Data Engineering team at customer has shared a sample set of transactions data set in CSV that they generate on nightly basis and the idea was to help them understand how to Ingest the data in CSV to Aiven Kafka via produced JSON messages. 
The scope of this tutorial material is to help guide the 
1. Data Engineering team about authoring an early version of Spring Kafka CSV ingest application, fully integrated with an Aiven Kafka Highly Available cluster on AWS US East-2 region for basic adoption purposes
2. Data Operations team about how Kafka cluster is integrated with InfluxDB and Grafana services offered by Aiven and understand out of box Kafka cluster metrics available in Grafana Dashboard

Below is _**out of scope**_ but can be a series of sessions with the customer's teams

0. Kafka and eco-system basics for up-rising Kafka Developer and Operator Adoption
1. How to set up Kafka Cluster and Security via automation, Infrastructure and Automation
2. How to on-board any existing Kafka client applications (such as Kafka Connectors, Plain Producer/Consumers, Kafka Streams/KSQL, etc)
3. How to deploy Kafka Client applications on the customer's Kubernetes Environment
4. Client's Real-time Data Platform and Event-Driven Architecture journey and design

## **Spring Kafka CSV Ingest Project**
### Important Code Snippets
Since springboot framework allows us to manage our codebase much more efficiently with using annotations, throughout the code one would see presence of several of those.

The scope of this documentation is to not articulate why, what and how to use annotation but that can be discussed as part of another Springboot 1-o-1 sessions.

Below sections will highlight some important aspects into the code base and also a little bit of details.
#### ProducerFactory Bean
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

## **Observability**

### **Aiven Grafana Service Login**
![alt_text](https://github.com/mpneural/aiven-takehome-demo-project/blob/master/assets/screenshots/aiven-grafana-service-login-page.png?raw=true)

### **Kafka Brokers Internal Metrics**

![alt text](https://github.com/mpneural/aiven-takehome-demo-project/blob/master/assets/screenshots/kafka-cluster-internal-metrics-1.png?raw=true)
