package com.weather.service;

import com.weather.service.model.Weather;
import com.weather.service.service.WeatherGrpcImplService;
import com.weather.service.service.WeatherService;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.testing.GrpcCleanupRule;
import org.apache.commons.compress.utils.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = {WeatherServiceApplication.class})
@SpringJUnitConfig
@SpringBootTest
@WebAppConfiguration
public class WeatherServiceIntegrationTest extends DbTestContainers {

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private WeatherGrpcImplService weatherGrpcImplService;

    private final GrpcCleanupRule grpcCleanup = new GrpcCleanupRule();

    @Test
    public void givenOneWeatherByIdInDb() throws IOException {
        insertWeathers();

        WeatherServiceGrpc.WeatherServiceBlockingStub blockingStub = weatherServiceBlockingStub();
        WeatherResponse weather = blockingStub.getOne(WeatherByIdRequest.newBuilder().setId(1L).build());

        assertThat(weather.getCloudiness().getValue()).isEqualTo("test1");
        assertThat(weather.getDegrees()).isEqualTo(25);
        assertThat(weather.getPressure().getValue()).isEqualTo("Pressure1");
    }

    @Test
    public void givenPageOfWeathersInDb() throws IOException {
        insertWeathers();
        WeatherServiceGrpc.WeatherServiceBlockingStub blockingStub = weatherServiceBlockingStub();

        PageWeatherRequest request = PageWeatherRequest.newBuilder()
                .setPage(0)
                .setSize(10)
                .build();

        Iterator<WeatherResponse> reply =
                blockingStub.getPage(request);

        List<WeatherResponse> actualList = Lists.newArrayList(reply);

        assertThat(actualList.size()).isEqualTo(2);
    }

    private WeatherServiceGrpc.WeatherServiceBlockingStub weatherServiceBlockingStub() throws IOException {
        String serverName = InProcessServerBuilder.generateName();
        // Create a server, add service, start, and register for automatic graceful shutdown.
        grpcCleanup.register(InProcessServerBuilder
                .forName(serverName).directExecutor()
                .addService(weatherGrpcImplService).build().start());

        // Create a client channel and register for automatic graceful shutdown.
        return WeatherServiceGrpc.newBlockingStub(
                grpcCleanup.register(InProcessChannelBuilder.forName(serverName).directExecutor().build())
        );
    }

    private void insertWeathers(){
        Weather weather1 = new Weather(1L, 25, "test1", "Pressure1", Date.valueOf(LocalDate.of(2013, 10, 10)));
        Weather weather2 = new Weather(2L, 25, "test2", "Pressure2", Date.valueOf(LocalDate.of(2013, 10, 10)));
        weatherService.save(weather1);
        weatherService.save(weather2);
    }
}
