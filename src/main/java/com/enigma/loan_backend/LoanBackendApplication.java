package com.enigma.loan_backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LoanBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoanBackendApplication.class, args);
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

}
