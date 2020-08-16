FROM maven:3-openjdk-8 AS build

ADD . .

RUN mvn package

FROM openjdk:8 AS runtime

EXPOSE 8080

WORKDIR /opt/app

COPY --from=build target/corona-monitor-0.0.1.jar /opt/app/corona-monitor.jar

ENTRYPOINT [ "java", "-jar", "corona-monitor.jar"]
