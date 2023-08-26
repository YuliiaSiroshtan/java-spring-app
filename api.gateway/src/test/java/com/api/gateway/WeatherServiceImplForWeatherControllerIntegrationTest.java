package com.api.gateway;

import com.google.protobuf.StringValue;
import com.weather.service.PageWeatherRequest;
import com.weather.service.WeatherResponse;
import com.weather.service.WeatherServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService()
public class WeatherServiceImplForWeatherControllerIntegrationTest extends WeatherServiceGrpc.WeatherServiceImplBase {

    @Override
    public void getPage(PageWeatherRequest request, StreamObserver<WeatherResponse> responseObserver){
        WeatherResponse response = WeatherResponse.newBuilder().build();

        responseObserver.onNext(response);
        WeatherResponse response2 = WeatherResponse.newBuilder()
                .setId(1)
                .setCloudiness(StringValue.of("test")).build();

        responseObserver.onNext(response2);
        responseObserver.onCompleted();
    }
}