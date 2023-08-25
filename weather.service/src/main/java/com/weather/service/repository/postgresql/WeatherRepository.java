package com.weather.service.repository.postgresql;

import com.weather.service.model.Weather;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface WeatherRepository extends JpaRepository<Weather, Long>,
        JpaSpecificationExecutor<Weather> {
    @Query(value = "SELECT id, degrees, cloudiness, date, pressure FROM Weather WHERE date = :date")
    Weather getByDate(LocalDate date);

    interface Specs {

        static Specification<Weather> greaterThanOrEqualToDegrees(int degrees) {
            return (root, query, builder) ->
                    builder.greaterThanOrEqualTo(root.get("degrees"), degrees);
        }

        static Specification<Weather> likeCloudiness(String cloudiness) {
            return (root, query, builder) ->
                    builder.like(root.get(cloudiness), cloudiness);
        }
    }
}
