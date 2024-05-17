package com.wjw.stock;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.wjw.stock.mapper")
public class StockBackendApp {
    public static void main(String[] args) {
        SpringApplication.run(StockBackendApp.class, args);
    }
}
