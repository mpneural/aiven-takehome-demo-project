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

##### Code Repository Structure
![alt_text](https://github.com/mpneural/aiven-takehome-demo-project/blob/master/assets/screenshots/source-code-structure.png?raw=true)


##### Debug Execution Output
```aidl

```

## **Observability**

### **Aiven Grafana Service Login**
![alt_text](https://github.com/mpneural/aiven-takehome-demo-project/blob/master/assets/screenshots/aiven-grafana-service-login-page.png?raw=true)

### **Kafka Brokers Internal Metrics**

![alt text](https://github.com/mpneural/aiven-takehome-demo-project/blob/master/assets/screenshots/kafka-cluster-internal-metrics-1.png?raw=true)
