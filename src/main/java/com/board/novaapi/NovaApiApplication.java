package com.board.novaapi;

import com.board.novaapi.Config.AppProperties;
import com.board.novaapi.Config.CorsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@EnableConfigurationProperties({
        CorsProperties.class,
        AppProperties.class
})
@EnableSwagger2
public class NovaApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(NovaApiApplication.class, args);
    }

}
