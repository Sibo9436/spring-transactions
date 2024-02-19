package com.sibo.transactions.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class TransactionsControllerConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TransactionInterceptors.RequestIdInterceptor());
        registry.addInterceptor(new TransactionInterceptors.LoggingInterceptor());
    }
}
