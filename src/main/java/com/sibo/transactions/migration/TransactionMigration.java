package com.sibo.transactions.migration;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class TransactionMigration {

    @Bean
    @ConditionalOnProperty("${spring.flyway.enabled}")
    CommandLineRunner migrate(@Autowired DataSource transactionDataSource) {
        return args ->{
            Flyway flyway = Flyway.configure().dataSource(transactionDataSource).validateMigrationNaming(true).load();
            flyway.migrate();
        } ;
    }
}
