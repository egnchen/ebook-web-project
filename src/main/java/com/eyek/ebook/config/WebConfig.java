package com.eyek.ebook.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    // allow cross origin visit from frontend which is under development
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods(new String[]{"GET", "POST", "PUT", "DELETE", "OPTIONS"})
                .allowedOrigins("http://127.0.0.1:8081", "http://localhost:8081");
    }
}
