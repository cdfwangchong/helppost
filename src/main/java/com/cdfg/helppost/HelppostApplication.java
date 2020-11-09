package com.cdfg.helppost;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.cdfg.helppost.dao")
public class HelppostApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelppostApplication.class, args);
    }

}
