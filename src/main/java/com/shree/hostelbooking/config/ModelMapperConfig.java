package com.shree.hostelbooking.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Set the matching strategy to STRICT to ensure deep mapping
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        // Enable skipping null values
      //  modelMapper.getConfiguration().setSkipNullEnabled(true);

        return modelMapper;
    }
}
