FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY target/ms-credits-transactions-0.0.1-SNAPSHOT.jar ./ms-credits-transactions.jar

EXPOSE 8087

CMD [ "java", "-jar", "ms-credits-transactions.jar" ]