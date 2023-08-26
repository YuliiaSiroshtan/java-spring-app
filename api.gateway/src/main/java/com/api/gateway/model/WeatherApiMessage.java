package com.api.gateway.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherApiMessage {
    public Long id;
    @NotNull(message = "Degrees field cannot be null!")
    public Integer degrees;
    public String cloudiness;
    public String pressure;
    @NotBlank(message = "Date cannot be blank!")
    public Date date;
}
