package com.example.demo;

import org.springframework.boot.validation.autoconfigure.ValidationConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidationConfig {

    @Bean
    public ValidationConfigurationCustomizer validationConfigurationCustomizer() {
        return conf -> conf.traversableResolver(new CustomTraversableResolver());
    }
}
