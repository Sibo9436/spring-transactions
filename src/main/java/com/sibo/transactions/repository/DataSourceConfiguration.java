package com.sibo.transactions.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

//Preferisco sempre usare questa classe piuttosto che avere una singola connessione possibile
@Configuration
public class DataSourceConfiguration {

    @Bean
    @ConfigurationProperties(  "spring.datasource.transactions")
    public DataSourceProperties transactionDataSourceProperties(){
        return new DataSourceProperties();
    }
    @Bean
    public DataSource transactionDataSource(){
        return transactionDataSourceProperties().initializeDataSourceBuilder().build();
    }
    @Bean
    public JdbcTemplate transactionJdbcTemplate(@Autowired DataSource transactionDataSource){
        return new JdbcTemplate(transactionDataSource);

    }
}
