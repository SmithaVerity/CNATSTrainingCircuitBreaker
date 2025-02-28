FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/service-b.jar service-b.jar
ENTRYPOINT ["java", "-jar", "service-b.jar"]
