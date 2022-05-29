package utils;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.*;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

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
                "bootstrap-url:port"); // bootstrap server URL
        configProps.put("security.protocol","SASL_SSL"); // kafka client security protocol
        configProps.put("ssl.client.auth","requested"); // Whether Kafka Producer client should authenticate with broker
        configProps.put("ssl.endpoint.identification.algorithm","https");
        configProps.put("ssl.enabled.protocols","TLSv1.2"); // SSL protocol version to be used (Min TLS version recommended is 1.2)
        configProps.put("sasl.mechanism","PLAIN"); // SASL mechanism
        configProps.put("ssl.truststore.location","/local/path/to/client-truststore-jks-file/"); // Location of truststore, the current keystore file format is jks
        configProps.put("ssl.truststore.password","truststore-password"); // password configured for truststore jks file
        configProps.put("sasl.jaas.config","org.apache.kafka.common.security.plain.PlainLoginModule required username='aiven-sasl-username' password='aiven-sasl-password';"); // SASL Plain Authentication credentials
        configProps.put("request.timeout.ms",20000);
        configProps.put(ProducerConfig.RECONNECT_BACKOFF_MAX_MS_CONFIG,1000);
        configProps.put(
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class); // key serializer class
        configProps.put(
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class); //value serializer class
        return new DefaultKafkaProducerFactory<>(configProps);
        /* // this will be for our k8s Kafka
        configProps.put(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "kafka.confluent.svc.cluster.local:9071");
        configProps.put("security.protocol","SSL");
        //configProps.put("ssl.client.auth","requested");
        //configProps.put("ssl.endpoint.identification.algorithm","https");
        configProps.put("ssl.enabled.protocols","TLSv1.2");
        //configProps.put("ssl.keystore.type","PKCS12");
        //configProps.put("ssl.truststore.type","PKCS12");
        configProps.put("ssl.truststore.location","/etc/service/secrets/truststore.jks");
        configProps.put("ssl.keystore.location","/etc/service/secrets/keystore.jks");
        configProps.put("ssl.truststore.password","changeme");
        configProps.put("ssl.keystore.password","changeme");
        configProps.put("ssl.key.password","changeme");
    */

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
