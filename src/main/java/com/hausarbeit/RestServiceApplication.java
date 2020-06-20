package com.hausarbeit;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// This adds: @Configuration, @EnableAutoConfiguration and @ComponentScan (scans rest of the package)
public class RestServiceApplication {
    public static void main(String[] args){
        SpringApplication.run(RestServiceApplication.class, args);
    }

}

