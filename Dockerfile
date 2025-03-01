FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/licorera-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app_licorera.jar
EXPOSE 1012
ENTRYPOINT ["java", "-jar", "app_licorera.jar"]
