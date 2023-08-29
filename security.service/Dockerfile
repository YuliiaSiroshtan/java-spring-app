FROM jelastic/maven:3.8.6-openjdk-20.ea-b24 AS build
ENV HOME=/usr/app
RUN mkdir -p $HOME
WORKDIR $HOME
ADD . $HOME
RUN mvn clean package -Dmaven.test.skip=true

FROM openjdk:20-jdk
COPY --from=build /usr/app/target/*.war /app/security.jar
EXPOSE 8083
ENTRYPOINT java -jar /app/security.jar