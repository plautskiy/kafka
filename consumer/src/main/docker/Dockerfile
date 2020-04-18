# Dockerfile for build image for consumer container
FROM openjdk:8
VOLUME /tmp
ADD target/consumer-1.0.0.jar consumer.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar","consumer.jar"]