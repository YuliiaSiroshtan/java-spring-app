FROM jelastic/maven:3.8.6-openjdk-20.ea-b24 AS build
RUN mkdir -p /usr/app
COPY pom.xml /usr/app
ENV HOME=/usr/app/wether
RUN mkdir -p $HOME
WORKDIR $HOME
ADD ./weather.service $HOME
RUN mvn clean package -Dmaven.test.skip=true

FROM openjdk:20-jdk
COPY --from=build /usr/app/wether/target/*.war /app/wether.war
EXPOSE 8082
ENTRYPOINT java -jar /app/wether.war