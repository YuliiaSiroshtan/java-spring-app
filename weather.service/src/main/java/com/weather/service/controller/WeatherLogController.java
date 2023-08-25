package com.weather.service.controller;

import com.weather.service.model.WeatherLog;
import com.weather.service.service.WeatherLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/weathers/logs")
public class WeatherLogController implements ExceptionHandlerInterface {
    private final WeatherLogService weatherLogService;

    @Autowired
    public WeatherLogController(
            WeatherLogService weatherLogService
    ) {
        this.weatherLogService = weatherLogService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<List<WeatherLog>> getLogs() {
        return ResponseEntity.ok(weatherLogService.getLogs());
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<WeatherLog> addNewMongo(@RequestBody WeatherLog weatherLog) {
        return ResponseEntity.ok(weatherLogService.addLog(weatherLog));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> deleteMongoById(@PathVariable String id) {
        weatherLogService.deleteLog(id);
        return ResponseEntity.ok(true);
    }
}
