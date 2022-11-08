FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY target/gateway-server-0.0.1-SNAPSHOT.jar ./gateway-server.jar

EXPOSE 8090

CMD [ "java", "-jar", "gateway-server.jar" ]