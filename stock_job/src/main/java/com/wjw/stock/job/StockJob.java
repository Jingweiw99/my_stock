package com.wjw.stock.job;

import com.wjw.stock.service.StockTimerTaskService;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class StockJob {
    @Autowired
    private StockTimerTaskService stockTimerTaskService;
    @XxlJob("wjw_job_test")
    public void jobTest() {
        log.info("当前时间为: {}", DateTime.now());
    }
    @XxlJob("getInnerMarketInfo")
    public void getInnerMarketInfo() {
        stockTimerTaskService.getInnerMarketInfo();
    }
}