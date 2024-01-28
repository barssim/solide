FROM openjdk:8-jdk-alpine
MAINTAINER solide.ma
WORKDIR /app
COPY target/solide-*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]