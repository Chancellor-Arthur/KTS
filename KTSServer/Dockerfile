#
# Build stage
#
FROM maven:3.9.5 AS build

ENV HOME=/usr/app

RUN mkdir -p $HOME
WORKDIR $HOME

ADD . $HOME

RUN mvn -f $HOME/pom.xml clean package -DskipTests

#
# Package stage
#
FROM eclipse-temurin:17

ARG JAR_FILE=/usr/app/target/*.jar
ARG STATIC_FILE=/usr/app/target/classes/static/*.xlsx

COPY --from=build $JAR_FILE /app/app.jar
COPY --from=build $STATIC_FILE /app/static/file.xlsx

EXPOSE 8080
ENTRYPOINT java -jar /app/app.jar