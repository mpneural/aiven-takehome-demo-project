package org.aiven.entry;

import org.aiven.examples.producer.LineProducer;
import org.springframework.context.annotation.ComponentScan;
import reader.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SuppressWarnings("ALL")
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
