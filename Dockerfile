FROM openjdk:17-jdk-slim

COPY build/libs/backEnd-mereles-0.0.1-SNAPSHOT.jar /app/app-mereles.jar

ENTRYPOINT ["java", "-jar", "/app/app-mereles.jar"]
