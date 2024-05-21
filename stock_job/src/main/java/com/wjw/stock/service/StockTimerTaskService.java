package com.wjw.stock.service;

public interface StockTimerTaskService {
    /**
     * 获取国内大盘的实时数据信息
     */
    void getInnerMarketInfo();
    void getStockRtIndex();
    /**
     * 板块实时数据采集功能
     */
    void getBlockInfo();
}