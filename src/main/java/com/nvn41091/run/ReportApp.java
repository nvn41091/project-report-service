package com.nvn41091.run;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ComponentScan("com.nvn41091")
@EnableJpaRepositories("com.nvn41091.repository")
@EntityScan(basePackages = "com.nvn41091.domain")
@EnableTransactionManagement
public class ReportApp {

    public static void main(String[] args) {
        SpringApplication.run(ReportApp.class, args);
    }

}
