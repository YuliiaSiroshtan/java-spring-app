package com.weather.service.service;

import com.weather.service.model.WeatherLog;
import com.weather.service.repository.mongo.WeatherLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeatherLogService {
    private final WeatherLogRepository weatherLogRepository;

    @Autowired
    public WeatherLogService(WeatherLogRepository weatherLogRepository) {
        this.weatherLogRepository = weatherLogRepository;
    }

    public List<WeatherLog> getLogs() {
        return weatherLogRepository.findAll();
    }

    public WeatherLog addLog(WeatherLog weatherLog) {
        return weatherLogRepository.save(weatherLog);
    }

    public void deleteLog(String id) {
        weatherLogRepository.deleteById(id);
    }
}
