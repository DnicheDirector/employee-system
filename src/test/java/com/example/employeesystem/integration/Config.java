package com.example.employeesystem.integration;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Bean
    public TestRestTemplate testRestTemplate(){
        return new TestRestTemplate().withBasicAuth("admin", "123456");
    }
}
