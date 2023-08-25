package com.weather.service.model;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("weather")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeatherLog {

    @Id
    public String id;
    public Integer weatherId;
    public String log;
}
