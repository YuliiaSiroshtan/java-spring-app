package com.api.gateway;

import com.vault.VaultModule;
import org.modelmapper.ModelMapper;
import org.modelmapper.protobuf.ProtobufModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(value = { VaultModule.class })
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper(){
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.registerModule(new ProtobufModule());

		return modelMapper;
	}

}
