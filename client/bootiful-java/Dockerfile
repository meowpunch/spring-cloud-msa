FROM adoptopenjdk/openjdk11:latest
VOLUME /tmp
ARG JAR_FILE='build/libs/bootiful-java-0.0.1-SNAPSHOT.jar'
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]