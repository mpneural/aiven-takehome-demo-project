
## **Observability**

### **Aiven Grafana Service Login**
![alt_text](https://github.com/mpneural/aiven-takehome-demo-project/blob/master/assets/screenshots/aiven-grafana-service-login-page.png?raw=true)


1. After you login we can go to Dashboards > Browse > General

2. We will see list of Kafka CLusters Dashobard

3. We can choose the Kafka CLuster under question to take a deep dive

### Cluster Overview Metrics

These set of metrics are aggregate count at a given point in time to reflect 

1. Cluster Uptime since (in mins)

2. Average Load per minute

3. Available Memory in MiB 
   
4. Available Disk Space aggregate

### Sysetem metrics
They provide insight about health of each Kafka broker node. Important metrics to consider :

1. Average CPU Load
2. Available Memory on Each Node
3. Number of Processes
4. Free Disc space per node
5. Disc iops
6. Network iops
7. Actual Disc usage by Kafka Topics

![alt_text](https://github.com/mpneural/aiven-takehome-demo-project/blob/master/assets/screenshots/cluster-general-metrics.png?raw=true)
### **Kafka Metrics**
These metrics provide insight about how Kafka cluster and internals are behaving. Characteristics to watch out for:
1. How many incoming messages
2. Total Requests
3. Read vs Write Requests
4. Bytes In vs Bytes Out
5. Under Replicated Partitions
6. Number of In-Sync Replicas
7. Unclean Leader Elections
8. Idle Handler Threads
9. Consumer Group Lag

![alt text](https://github.com/mpneural/aiven-takehome-demo-project/blob/master/assets/screenshots/kafka-cluster-internal-metrics-1.png?raw=true)

**NOTE :** Some other metrics that clients are suggested to also build their custom dashboards about should be:

### Java Metrics
The out-of-box provided Java Metrics are:
1. Memory Usage
2. Garbage Collection Count
3. Garbage Collection Duration

### Custom Metrics
While our out-of-box dashboard is a good starting point for Kafka oriented metrics, we also suggest some available open-srouce metrics that you can also configure for your client applications such as producers, consumers, connectors, kafka streams and ksqldb

Here's the link : https://github.com/confluentinc/cp-helm-charts/blob/master/grafana-dashboard/confluent-open-source-grafana-dashboard.json