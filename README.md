# PetStore Project


# Kafka commands (local)
1. start zookeeper: <br> 
zookeeper-server-start.sh ~/Documents/kafka_2.13-3.6.1/config/zookeeper.properties
2. start server: <br>
kafka-server-start.sh ~/Documents/kafka_2.13-3.6.1/config/server.properties
3. create a topic: <br>
kafka-topics.sh --bootstrap-server 0.0.0.0:9092 --create --topic users --partitions 3
4. check console: <br>
kafka-console-consumer.sh --bootstrap-server 0.0.0.0:9092 --topic users --from-beginning

# Kafka commands (for window)
1. start zookeeper: <br>
   zookeeper-server-start.sh ~/kafka_2.13-3.6.1/config/zookeeper.properties
2. start server: <br>
   kafka-server-start.sh ~/kafka_2.13-3.6.1/config/server.properties
3. create a topic: <br>
   kafka-topics.sh --bootstrap-server localhost:9092 --create --topic users --partitions 3
4. check console: <br>
   kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic users --from-beginning
5. Disable ipv6: <br>
   sudo sysctl -w net.ipv6.conf.all.disable_ipv6=1
   sudo sysctl -w net.ipv6.conf.default.disable_ipv6=1

