package pl.wiktorstrzelczyk.recruitment.task.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@EnableAsync
public class AppConfig {

    @Bean
    public ExecutorService fixedThreadPool() {
        return Executors.newFixedThreadPool(5);
    }
}