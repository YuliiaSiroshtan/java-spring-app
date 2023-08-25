package com.weather.service;

import com.weather.service.service.WeatherGrpcImplService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.modelmapper.ModelMapper;
import org.modelmapper.protobuf.ProtobufModule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.io.IOException;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class WeatherServiceApplication {

    private static Integer grpcPort;

    @Value("${grpc.port}")
    public void setGrpcPort(Integer port){
        grpcPort = port;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        SpringApplication.run(WeatherServiceApplication.class, args);

        Server server = ServerBuilder
                .forPort(grpcPort)
                .addService(SpringContext.getBean(WeatherGrpcImplService.class))
                .build();

        server.start();
        server.awaitTermination();
    }

    @Bean
    public AuditorAware<Integer> auditorAware() {
        return new AuditorAwareImpl();
    }

    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.registerModule(new ProtobufModule());

        return modelMapper;
    }
}
