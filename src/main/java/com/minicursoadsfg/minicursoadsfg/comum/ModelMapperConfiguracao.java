package com.minicursoadsfg.minicursoadsfg.comum;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfiguracao {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
}
