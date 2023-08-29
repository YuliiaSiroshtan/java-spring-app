FROM jelastic/maven:3.8.6-openjdk-20.ea-b24 AS build
RUN mkdir -p /usr/app
COPY pom.xml /usr/app
ENV HOME=/usr/app/gateway
RUN mkdir -p $HOME
WORKDIR $HOME
ADD ./api.gateway $HOME
RUN mvn package

FROM openjdk:20-jdk
COPY --from=build /usr/app/gateway/target/*.jar /app/gateway.jar
EXPOSE 8080
ENTRYPOINT java -jar /app/gateway.jar