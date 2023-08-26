package com.api.gateway.service;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public abstract class BaseService<T> implements GatewayService<T> {
    @Value("${weather.service.host}")
    private String weatherServiceGrpcHost;
    @Value("${weather.service.port}")
    private String weatherServiceGrpcPort;
    protected ManagedChannel channel;

    @PostConstruct
    private void initChannel() {
        this.channel = ManagedChannelBuilder
                .forAddress(weatherServiceGrpcHost,
                        Integer.parseInt(weatherServiceGrpcPort))
                .usePlaintext()
                .build();
    }
}
