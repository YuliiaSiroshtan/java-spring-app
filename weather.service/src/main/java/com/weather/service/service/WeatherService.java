package com.weather.service.service;

import com.weather.service.exception.NotFoundException;
import com.weather.service.model.Weather;
import com.weather.service.model.request.SinglePageRequest;
import com.weather.service.repository.postgresql.WeatherRepository;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static com.weather.service.repository.postgresql.WeatherRepository.Specs.greaterThanOrEqualToDegrees;
import static com.weather.service.repository.postgresql.WeatherRepository.Specs.likeCloudiness;

@Service
@Log4j
public class WeatherService {
    private final WeatherRepository weatherRepository;

    @Autowired()
    public WeatherService(
            WeatherRepository weatherRepository
    ) {
        this.weatherRepository = weatherRepository;
    }

    public Page<Weather> getPaged(SinglePageRequest pageRequest) {
        if (Objects.isNull(pageRequest)) {
            log.warn("Page model is null");
            pageRequest = new SinglePageRequest();
        }

        Sort pageSort = Sort.by(pageRequest.getSortDirection(), "date");
        Pageable pageable = PageRequest.of(pageRequest.getPageNumber(), pageRequest.getPageSize(), pageSort);

        return weatherRepository.findAll(pageable);
    }

    public Weather getOne(Long id){
        return weatherRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public Weather save(Weather weather) {
        return weatherRepository.save(weather);
    }

    public List<Weather> find(Weather weather){
        return weatherRepository.findAll(
                greaterThanOrEqualToDegrees(weather.getDegrees())
                .and(likeCloudiness(weather.getCloudiness())));
    }

    public void delete(Long id) {
        weatherRepository.deleteById(id);
    }

    public Weather getByDate(LocalDate date){
        return weatherRepository.getByDate(date);
    }
}
