FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/aurora-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app_aurora.jar
EXPOSE 1012
ENTRYPOINT ["java", "-jar", "app_aurora.jar"]
