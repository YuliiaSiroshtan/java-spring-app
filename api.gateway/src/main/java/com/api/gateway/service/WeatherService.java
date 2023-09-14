package com.api.gateway.service;

import com.google.common.collect.Lists;
import com.google.protobuf.StringValue;
import com.api.gateway.model.WeatherApiMessage;
import com.api.gateway.model.WeatherServiceProperties;
import com.weather.service.*;
import lombok.Setter;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Iterator;
import java.util.List;

@Service
public class WeatherService extends BaseService<WeatherApiMessage> {
    private final ModelMapper modelMapper;

    @Setter
    private WeatherServiceGrpc.WeatherServiceBlockingStub stub;

    @PostConstruct
    private void init(){
        this.stub = WeatherServiceGrpc.newBlockingStub(channel);
    }

    @Autowired
    public WeatherService(ModelMapper modelMapper, WeatherServiceProperties weatherServiceProperties) {
        this.modelMapper = modelMapper;
    }

    public WeatherApiMessage findWeatherForDate(Integer day, Integer month, Integer year) {
        try {
            return modelMapper.map(stub.weatherByDate(WeatherByDateRequest.newBuilder()
                    .setDay(day)
                    .setMonth(month)
                    .setYear(year)
                    .build()), WeatherApiMessage.class);
        }
        catch (Exception ex){
            var message = ex.getMessage();
            return null;
        }
    }

    public Iterator<WeatherApiMessage> getAll(int size, int page) {
//        Iterator<WeatherResponse> response = stub.getPage(PageWeatherRequest.newBuilder()
//                .setSize(size)
//                .setPage(page)
//                .build());
//
//        return Lists.newArrayList(response)
//                .stream()
//                .map(item -> modelMapper.map(item, WeatherApiMessage.class))
//                .iterator();

        return List.of(new WeatherApiMessage()).listIterator();
    }

    public WeatherApiMessage getOne(Integer id) {
        return modelMapper.map(stub.getOne(WeatherByIdRequest.newBuilder()
                .setId(id)
                .build()), WeatherApiMessage.class);
    }

    @Override
    public WeatherApiMessage save(WeatherApiMessage object) {
        WeatherResponse response = stub.save(modelMapper.map(object, FullWeatherRequest.Builder.class).build());

        return modelMapper.map(response, WeatherApiMessage.class);
    }

    @Override
    public WeatherApiMessage update(WeatherApiMessage object) {
        WeatherResponse response = stub.update(modelMapper.map(object, FullWeatherRequest.Builder.class).build());
        return modelMapper.map(response, WeatherApiMessage.class);
    }

    public Boolean delete(Integer id) {
        return stub.delete(WeatherByIdRequest.newBuilder()
                .setId(id).build()).getResult();
    }

    public Iterator<WeatherApiMessage> find(String field) {
        Iterator<WeatherResponse> response = stub.find(FullWeatherRequest.newBuilder()
                .setCloudiness(field != null ? StringValue.of(field) : null)
                .build());

        return Lists.newArrayList(response)
                .stream()
                .map(item -> modelMapper.map(item, WeatherApiMessage.class))
                .iterator();
    }
}
