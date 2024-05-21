package com.wjw.stock.pojo.domain;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Data
@ConfigurationProperties(prefix = "stock")
public class StockInfoConfig {
    /**
     * 国内大盘编码集合 上证和深证
     */
    private List<String> inner;

    /**
     * 外盘相关编码集合
     */
    private List<String> outer;
    //股票区间
    private List<String> upDownRange;
    //大盘参数获取url
    private String marketUrl;
    //板块参数获取url
    private String blockUrl;
}
