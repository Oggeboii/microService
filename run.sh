#!/bin/sh

java --module-path \
  serviceConsumer/target/serviceConsumer-1.0-SNAPSHOT.jar:service/target/service-1.0-SNAPSHOT.jar:serviceProvider/target/serviceProvider-1.0-SNAPSHOT.jar \
  -m com.example.serviceConsumer/com.example.consumer.CurrencyConverter.java
