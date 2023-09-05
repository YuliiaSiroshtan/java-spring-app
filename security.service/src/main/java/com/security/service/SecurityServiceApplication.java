package com.security.service;

import com.vault.VaultModule;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(value = { VaultModule.class })
public class SecurityServiceApplication {

    public static void main(String... args) {
        SpringApplication.run(SecurityServiceApplication.class);
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
