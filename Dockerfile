FROM maven:3.6.3-openjdk-11-slim

WORKDIR /

COPY ./ ./
RUN mvn clean package

ENTRYPOINT ["java","-jar","target/technical-assignment-0.0.1-SNAPSHOT.jar"]
