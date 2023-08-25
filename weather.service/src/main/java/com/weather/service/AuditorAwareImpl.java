package com.weather.service;

import org.springframework.data.domain.AuditorAware;

import java.util.Arrays;
import java.util.Optional;
import java.util.Random;

public class AuditorAwareImpl implements AuditorAware<Integer> {

    @Override
    public Optional<Integer> getCurrentAuditor() {
        // todo. use Spring Security to retrieve the currently logged-in user(s)
        return Optional.of(Arrays.asList(321, 123, 111)
                .get(new Random().nextInt(3)));
    }
}
