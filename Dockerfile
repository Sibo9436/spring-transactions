FROM openjdk:17-alpine
COPY target target
ENTRYPOINT ["java","-jar", "target/transactions-0.0.1-SNAPSHOT.jar"]