# PetStore Project


# Kafka commands (local)
start zookeeper: zookeeper-server-start.sh ~/Documents/kafka_2.13-3.6.1/config/zookeeper.properties
start server: kafka-server-start.sh ~/Documents/kafka_2.13-3.6.1/config/server.properties
create a topic: kafka-topics.sh --bootstrap-server 0.0.0.0:9092 --create --topic users --partitions 3
check console: kafka-console-consumer.sh --bootstrap-server 0.0.0.0:9092 --topic users --from-beginning

