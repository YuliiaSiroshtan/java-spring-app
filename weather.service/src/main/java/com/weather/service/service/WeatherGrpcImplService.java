package com.weather.service.service;

import com.google.protobuf.Any;
import com.google.rpc.ErrorInfo;
import com.weather.service.*;
import com.weather.service.model.Weather;
import com.weather.service.model.request.SinglePageRequest;
import io.grpc.protobuf.StatusProto;
import io.grpc.stub.StreamObserver;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.server.service.GrpcService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

@GrpcService
@Log4j2
public class WeatherGrpcImplService extends WeatherServiceGrpc.WeatherServiceImplBase {
    private final WeatherService weatherService;
    private final ModelMapper modelMapper;

    @Autowired
    public WeatherGrpcImplService(
            WeatherService weatherService,
            ModelMapper modelMapper) {
        this.weatherService = weatherService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void getPage(PageWeatherRequest request, StreamObserver<WeatherResponse> responseObserver) {
        SinglePageRequest pageRequest = new SinglePageRequest()
                .toBuilder()
                .pageNumber(request.getPage())
                .pageSize(request.getSize())
                .build();

        Page<Weather> weathers = weatherService.getPaged(pageRequest);

        for (Weather item : weathers) {
            WeatherResponse response = modelMapper.map(item, WeatherResponse.Builder.class).build();
            responseObserver.onNext(response);
        }

        responseObserver.onCompleted();
    }

    @Override
    public void getOne(WeatherByIdRequest request, StreamObserver<WeatherResponse> responseObserver) {
        Weather weather = weatherService.getOne(request.getId());

        if (weather == null) {
            com.google.rpc.Status status = com.google.rpc.Status.newBuilder()
                    .setCode(com.google.rpc.Code.NOT_FOUND.getNumber())
                    .setMessage("Weather not found")
                    .addDetails(Any.pack(ErrorInfo.newBuilder()
                            .setReason("Weather not found")
                            .putMetadata("id", String.valueOf(request.getId()))
                            .build()))
                    .build();
            responseObserver.onError(StatusProto.toStatusRuntimeException(status));
            responseObserver.onCompleted();
            return;
        }

        WeatherResponse response = modelMapper.map(weather, WeatherResponse.Builder.class).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void find(FullWeatherRequest request, StreamObserver<WeatherResponse> responseObserver) {
        List<Weather> weathers = weatherService.find(modelMapper.map(request, Weather.class));

        for (Weather item : weathers) {
            WeatherResponse response = modelMapper.map(item, WeatherResponse.Builder.class).build();
            responseObserver.onNext(response);
        }

        responseObserver.onCompleted();
    }

    @Override
    public void save(FullWeatherRequest request, StreamObserver<WeatherResponse> responseObserver) {
        Weather weather = weatherService.save(modelMapper.map(request, Weather.class));
        WeatherResponse response = modelMapper.map(weather, WeatherResponse.Builder.class).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void update(FullWeatherRequest request, StreamObserver<WeatherResponse> responseObserver) {
        Weather weatherToUpdate = weatherService.getOne(request.getId());

        weatherToUpdate.setCloudiness(String.valueOf(request.getCloudiness()));
        weatherToUpdate.setPressure(String.valueOf(request.getPressure()));
        weatherToUpdate.setDegrees(request.getDegrees());

        weatherToUpdate = weatherService.save(weatherToUpdate);

        WeatherResponse response = modelMapper.map(weatherToUpdate, WeatherResponse.Builder.class).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void delete(WeatherByIdRequest request, StreamObserver<WeatherBooleanResponse> responseObserver) {
        weatherService.delete(request.getId());

        WeatherBooleanResponse response = WeatherBooleanResponse.newBuilder()
                .setResult(true)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void weatherByDate(WeatherByDateRequest request, StreamObserver<WeatherResponse> responseObserver) {
        LocalDate date = LocalDate.of(request.getYear(), request.getMonth(), request.getDay());
        Weather weather = weatherService.getByDate(date);

//        if (weather == null) {
//            com.google.rpc.Status status = com.google.rpc.Status.newBuilder()
//                    .setCode(com.google.rpc.Code.NOT_FOUND.getNumber())
//                    .setMessage("Weather not found")
//                    .addDetails(Any.pack(ErrorInfo.newBuilder()
//                            .setReason("Weather not found")
//                            .putMetadata("date", String.valueOf(date))
//                            .build()))
//                    .build();
//            responseObserver.onError(StatusProto.toStatusRuntimeException(status));
//            responseObserver.onCompleted();
//            return;
//        }

        WeatherResponse response = modelMapper.map(weather, WeatherResponse.Builder.class).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}