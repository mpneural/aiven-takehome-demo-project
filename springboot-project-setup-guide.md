# Tutorial - Ingesting Transactions into Aiven Kafka via Spring Kafka App
## Project Setup Guide
### Pre-Requisite
1. JDK >=11
2. Gradle >=7.3
3. Your Favorite IDE (such as IntelliJ IDEA or VSCode)
4. Bash or Shell
5. Keytool Utility that was part of JDK install

### Setup
1. Check out the code from Github repository here : https://github.com/mpneural/aiven-takehome-demo-project.git
   

2. Ensure that Aiven Kafka Cluster is set up and running : https://github.com/mpneural/aiven-takehome-demo-project/blob/master/assets/screenshots/running-aiven-services.png
   

3. Capture Aiven Kafka Cluster Connection Properties and Security Credentials : https://github.com/mpneural/aiven-takehome-demo-project/blob/master/assets/screenshots/aiven-kafka-cluster-sasl-ssl.png

    
    The required parameters are :
   
    3.1 kafka cluster bootstrap server URL -- Service URI from Aiven Console

    3.2 SASL Plain Login Username -- User from Aiven Console

    3.3 SASL Plain Login Password -- Password from Aiven Console

    3.4 Download CA Certificate 

   
4. Setup Kafka client truststore

    4.1 Go to project's src directory and under resources directory create a directory named certs
    
    4.2 Copy the downloaded ca.pem file 
    
    4.3 In the folder where the certificates are stored, use the keytool utility to create the truststore with the ca.pem file as input:
   ```aidl 
        keytool -import  \
        -file ca.pem \
        -alias CA    \
        -keystore client.truststore.jks

    ```

    4.4 You will be asked to provide keystore password so that only specific applications having the credentials can access the truststore jks certificate

        Enter keystore password:  
        Re-enter new password: 

    4.5 Once the password is set, one will see output similar to below. Kindly ensure default validity of the certificate for ~1 year.
        
        Owner: CN=d062e51c-3281-4e70-9129-9d186d9128a3 Project CA
        Issuer: CN=d062e51c-3281-4e70-9129-9d186d9128a3 Project CA
        Serial number: 296e68d7273e041706d922667ddff140efe5d804
        Valid from: Sat May 28 16:18:20 ADT 2022 until: Tue May 25 16:18:20 ADT 2032

5. Update Aiven Kafka connection properties in the project ProducerFactory class
    
    5.1 In the prject code base go to _src > main > java > utils > ProducerConfiguration.java_ and replace the values of below key proerpties
   
        configProps.put(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "bootstrap-url:port"); // bootstrap server URL

        configProps.put("ssl.truststore.location","/local/path/to/client-truststore-jks-file/"); // Location of truststore, the current keystore file format is jks
        
        configProps.put("ssl.truststore.password","truststore-password"); // password configured for truststore jks file
        
        configProps.put("sasl.jaas.config","org.apache.kafka.common.security.plain.PlainLoginModule required username='aiven-sasl-username' password='aiven-sasl-password';"); // SASL Plain Authentication credentials

6. Please ensure that transactions.csv file is bundled within the resources
    
    6.1 In the project code base go to : _src > main > resources_ and find transactions.csv file
   
7. Perform below commands
   
         gradle clean
         gradle build

8. We can manually go to the main method of the Springboot Entrypoint class and either execute the application in debug or run mode from IntelliJ

   8.1 In the project code base go to : _src > main > java > or.avien > entry > CSVProducerApp.java_
   
   8.2 Choose either Debug or Run options from the below

   ![alt_text](https://github.com/mpneural/aiven-takehome-demo-project/blob/master/assets/screenshots/springboot-java-main-execution-options.png?raw=true) 
   