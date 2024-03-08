FROM openjdk:17-alpine

ARG PORT=8877
ARG APP_VERSION=1.0.0

ENV APP_VERSION=$APP_VERSION
ENV PORT=$PORT

RUN mkdir /application
COPY ./target/*.jar /application/application.jar
WORKDIR /application

EXPOSE 8877

CMD ["java", "-jar", "application.jar"]
