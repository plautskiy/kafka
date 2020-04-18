# Dockerfile for build image for producer container
FROM openjdk:8
VOLUME /tmp
ADD target/producer-1.0.0.jar producer.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","producer.jar"]