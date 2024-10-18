package com.nezdanchik.pp3_1_5.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {


    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
