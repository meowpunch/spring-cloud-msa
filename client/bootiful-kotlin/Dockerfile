FROM adoptopenjdk/openjdk11:latest
VOLUME /tmp
ARG JAR_FILE='build/libs/bootiful-kotlin-0.0.1-SNAPSHOT.jar'
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
