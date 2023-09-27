package com.minsait.treinamento.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@ConditionalOnProperty(matchIfMissing = true, prefix = "app.scheduler", name = "ativo")
public class SchedulerConfig {

}
