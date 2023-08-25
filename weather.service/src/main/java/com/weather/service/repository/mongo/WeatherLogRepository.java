package com.weather.service.repository.mongo;

import com.weather.service.model.WeatherLog;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WeatherLogRepository extends MongoRepository<WeatherLog, String> {

}
