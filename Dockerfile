FROM openjdk:24-jdk

LABEL maintainer="Oggeboii" \
version=1.0 \
description="A Java SPI for currency convertion"

WORKDIR /app

COPY serviceConsumer/target/serviceConsumer-1.0-SNAPSHOT.jar serviceConsumer.jar
COPY service/target/service-1.0-SNAPSHOT.jar service.jar
COPY serviceProvider/target/serviceProvider-1.0-SNAPSHOT.jar serviceProvider.jar

EXPOSE 8080

ENTRYPOINT ["java", "--module-path", "serviceConsumer.jar:service.jar:serviceProvider.jar", "-m", "com.example.serviceConsumer/com.example.consumer.CurrencyConverter"]
