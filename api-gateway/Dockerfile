FROM openjdk:17-jdk-alpine
EXPOSE 8080
ARG GOOGLE_CLIENTSECRET
ARG GOOGLE_CLIENTID
ARG GOOGLE_CLIENTSECRET=$GOOGLE_CLIENTSECRET
ARG GOOGLE_CLIENTID=$GOOGLE_CLIENTID
ADD target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]