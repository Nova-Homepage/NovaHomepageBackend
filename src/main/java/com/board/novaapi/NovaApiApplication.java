package com.board.novaapi;

import com.board.novaapi.Config.AppProperties;
import com.board.novaapi.Config.CorsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@SpringBootApplication
@EnableConfigurationProperties({
        CorsProperties.class,
        AppProperties.class
})
public class NovaApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(NovaApiApplication.class, args);
    }

}
