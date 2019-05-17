FROM openjdk:12-jdk-alpine
VOLUME /tmp
ADD target/mgb-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
