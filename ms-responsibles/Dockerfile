FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY target/ms-responsibles-0.0.1-SNAPSHOT.jar ./ms-responsibles.jar

EXPOSE 8085

CMD [ "java", "-jar", "ms-responsibles.jar" ]