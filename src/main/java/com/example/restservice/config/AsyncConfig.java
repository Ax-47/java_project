package com.example.restservice.config;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AsyncConfig {

  @Bean(name = "imageExecutor")
  public Executor imageExecutor() {
    return Executors.newFixedThreadPool(8);
  }
}
