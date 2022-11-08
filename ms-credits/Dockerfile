FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY target/ms-credits-0.0.1-SNAPSHOT.jar ./ms-credits.jar

EXPOSE 8086

CMD [ "java", "-jar", "ms-credits.jar" ]