package com.hausarbeit;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class RestServiceApplication {

    public static void main(String[] args) throws Exception {
        new SpringApplicationBuilder()
                .sources(RestServiceApplication.class)
                .run(args);
    }
}

