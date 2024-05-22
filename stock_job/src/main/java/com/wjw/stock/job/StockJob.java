package com.wjw.stock.job;

import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class StockJob {
    @XxlJob("wjw_job_test")
    public void jobTest() {
        log.info("当前时间为: {}", DateTime.now());
    }
}