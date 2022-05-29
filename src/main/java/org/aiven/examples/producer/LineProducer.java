package org.aiven.examples.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.FinancialTransactionsMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import utils.ProducerConfiguration;
import java.util.UUID;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
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
            // kafkaTemplate.send("a_htl_bookings", lineMessage.getId(),objectMapper.writeValueAsString(lineMessage));
            kafkaTemplate.send("financial_transaction", String.valueOf(UUID.randomUUID()), objectMapper.writeValueAsString(lineMessage));
            logger.info("Line with id: {} sent", lineMessage.getTransaction_id());
        } catch (JsonProcessingException e) {
            logger.error("Error while serializing " + lineMessage.toString(), e);
        }
    }
}
