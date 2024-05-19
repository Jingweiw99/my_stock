package com.wjw.stock;

import com.wjw.stock.pojo.domain.StockInfoConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({StockInfoConfig.class})
@MapperScan("com.wjw.stock.mapper")
public class StockBackendApp {
    public static void main(String[] args) {
        SpringApplication.run(StockBackendApp.class, args);
    }
}
