package org.example.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Configuration
public class AgendamentoConfig {

    @Bean
    public ScheduledExecutorService agendaTarefa(){
        return Executors.newScheduledThreadPool(10);
    }
}
