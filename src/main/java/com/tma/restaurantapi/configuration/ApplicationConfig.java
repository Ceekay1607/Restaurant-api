package com.tma.restaurantapi.configuration;

import com.tma.restaurantapi.model.converter.BillDetailsConverter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Model mapper configuration
 */
@Configuration
public class ApplicationConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(new BillDetailsConverter());
        return modelMapper;
    }

}
