package com.eyek.ebook.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    // model mapper for dto - entity translation
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
