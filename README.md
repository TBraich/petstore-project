# PetStore Project

## Introduction

Welcome to my ShowReel project! <br>
This repository contains a comprehensive showcase of a pet-store management system
including microservices applications (user, user-auth, user-core, pet), designed to demonstrate modern software
development practices including microservices architecture, message queuing, caching, and secure authentication
mechanisms.

## Getting Started

### Environment Setup

If you want to test locally, refer section *For testing* for preparing servers.

1. **Redis**: For caching the information
2. **MySQL**: Database
3. **Kafka-server**: For message/queue, applications with the suffix -core (port: 80x1) will normally need this
4. **Kafka-zookeeper**: For message/queue, applications with the suffix -core (port: 80x1) will normally need this

### For Testing:

For testing locally, you must start the essential services:

1. Navigate to the `./dev/` directory.
2. Ensure Docker is running on your machine.
3. Execute `docker compose up` to start the services.

**Environment variables/env**: Environment variables are set with default values for local testing.

### For Production:

For releasing, you will need to update these variables/env:

1. **For Kafka**: <br>
   `1. kafka.bootstrap-servers` : bootstrap server host of Kafka-server <br>
   `2. kafka.group-id` : group-id of Kafka <br>
2. **For Authorization**: <br>
   `1. petstore.app.auth.host` : Authorization APIs host <br>
   `2. petstore.app.auth.authorize` : Authorization API path (ref: user-auth) <br>
   `3. petstore.app.auth.path` : Role Path API path (ref: user-auth) <br>
   `4. petstore.app.jwt-secret` : secretKey for encrypt/decrypt the JWT token <br>
3. **For Redis**: <br>
   `1. spring.redis.host` : Redis host <br>
   `2. spring.redis.port` : Redis port <br>
   `3. spring.redis.password` : Redis password <br>
4. **For Auth0**: <br>
   In dev environment, Auth0 will have a stub bean, but in production, please update these:
   `1. auth0.domain` <br>
   `2. auth0.audience`<br>
   `3. auth0.client-id`<br>
   `3. auth0.client-secret`<br>
   `3. auth0.connection`<br>
5. **DB Migration (Flyway configuration)**: <br>
   `1. spring.flyway.dataSource.url` : Database URL <br>
   `2. spring.flyway.dataSource.username` : Database username <br>
   `3. spring.flyway.dataSource.password` : Database password <br>
   `4. spring.flyway.dataSource.driver-class-name` : Database Driver <br>
6. **DB Configuration (MyBatis/JPA)**: <br>
   `1. spring.dataSource.url` : Database URL <br>
   `2. spring.dataSource.username` : Database username <br>
   `3. spring.dataSource.password` : Database password <br>
   `4. spring.dataSource.driver-class-name` : Database Driver <br>
   `5. (Optional) spring.dataSource.hikari.connection-timeout` : DB connection timeout <br>
   `6. (Optional) spring.dataSource.hikari.idle-timeout` : DB idling timeout <br>
   `7. (Optional) spring.dataSource.hikari.maximum-pool-size` : DB max pool size <br>

### Docker Image Building

Building Docker images with maven plugin jib-maven-plugin, (JDK: openjdk:17-oracle) <br>

1. There are two env variables that you need to set up first when building the application images: <br>
   `DOCKER_USER_NAME` : Image Registry username <br>
   `DOCKER_USER_PASSWORD` : Image Registry password
2. (Optional) If you want to push the image to your own repository, update `plugin.configuration.to.image`

## Addition

### Kafka commands (MacOS/Linux)

1. start zookeeper: <br>
   `zookeeper-server-start.sh ~/Documents/kafka_2.13-3.6.1/config/zookeeper.properties`
2. start server: <br>
   `kafka-server-start.sh ~/Documents/kafka_2.13-3.6.1/config/server.properties`
3. create a topic: <br>
   `kafka-topics.sh --bootstrap-server 0.0.0.0:9092 --create --topic users --partitions 3`
4. check console: <br>
   `kafka-console-consumer.sh --bootstrap-server 0.0.0.0:9092 --topic users --from-beginning`

## Kafka commands (for window)

1. start zookeeper: <br>
   `zookeeper-server-start.sh ~/kafka_2.13-3.6.1/config/zookeeper.properties`
2. start server: <br>
   `kafka-server-start.sh ~/kafka_2.13-3.6.1/config/server.properties`
3. create a topic: <br>
   `kafka-topics.sh --bootstrap-server localhost:9092 --create --topic users --partitions 3`
4. check console: <br>
   `kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic users --from-beginning`
5. Disable ipv6: <br>
   `sudo sysctl -w net.ipv6.conf.all.disable_ipv6=1` <br>
   `sudo sysctl -w net.ipv6.conf.default.disable_ipv6=1`

---

**Contributing**

Contributions to the PetStore project are always welcome! <3

**License**

This project is licensed under the [MIT License](LICENSE). See the LICENSE file for details.