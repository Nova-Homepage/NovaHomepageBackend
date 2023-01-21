package com.board.novaapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class NovaApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(NovaApiApplication.class, args);
    }

}
