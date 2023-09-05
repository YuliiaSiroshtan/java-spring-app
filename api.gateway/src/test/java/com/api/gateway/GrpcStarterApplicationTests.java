package com.api.gateway;

import com.api.gateway.service.WeatherService;
import com.weather.service.WeatherServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@AutoConfigureMockMvc(addFilters = false)
@SpringJUnitConfig(classes = { ServiceIntegrationTestConfiguration.class })
@DirtiesContext
@SpringBootTest(properties = {
        "grpc.server.inProcessName=test",
        "grpc.server.port=-1",
        "grpc.client.weatherService.address=in-process:test"
})
public class GrpcStarterApplicationTests {

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private WeatherService weatherService;
    @GrpcClient("weatherService")
    private WeatherServiceGrpc.WeatherServiceBlockingStub stub;
    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        this.weatherService.setStub(stub);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).apply(springSecurity()).build();
    }

    @Test
    @DirtiesContext
    @WithMockUser(authorities = { "canWeatherReadWrite" })
    public void givenPageOfWeathers_whenMockMVC_thenResponseOK() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/api/weather/?size=10&page=0"))
                .andDo(print()).andExpect(status().isOk());
    }
}