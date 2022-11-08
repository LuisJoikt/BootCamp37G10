FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY target/ms-accounts-transactions-0.0.1-SNAPSHOT.jar ./ms-accounts-transactions.jar

EXPOSE 8089

CMD [ "java", "-jar", "ms-accounts-transactions.jar" ]