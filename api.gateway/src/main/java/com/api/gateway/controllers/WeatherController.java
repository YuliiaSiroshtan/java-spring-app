package com.api.gateway.controllers;

import com.api.gateway.model.WeatherApiMessage;
import com.api.gateway.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/weather/")
public class WeatherController extends CRUDController<WeatherApiMessage> {
    private final WeatherService service;

    @Autowired
    public WeatherController(WeatherService service) {
        super(service);
        this.service = service;
    }

    @GetMapping("/find/for-date")
    public WeatherApiMessage getWeatherForDate(@RequestParam(required = false) Integer day,
                                               @RequestParam(required = false) Integer month,
                                               @RequestParam(required = false) Integer year) {
        return service.findWeatherForDate(day, month, year);
    }
}
