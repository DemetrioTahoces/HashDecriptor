package com.demetrio.hashdecriptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
@Slf4j
public class HashDecryptApplication {

    public static void main(String[] args) {
        SpringApplication.run(HashDecryptApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(ApplicationContext appContext) {
        return args -> Arrays.stream(appContext.getBeanDefinitionNames()).sorted().forEach(log::info);
    }

}
