package com.maxrocky.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@Configuration
public class CrossFilter extends WebMvcConfigurerAdapter {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //"http://localhost:8082","http://localhost:8085",
        String[] origins = {"*"};
        registry.addMapping("/**")
                .allowedOrigins(origins)
                .allowCredentials(true)
                .allowedMethods("*")
                .maxAge(3600);
    }

}