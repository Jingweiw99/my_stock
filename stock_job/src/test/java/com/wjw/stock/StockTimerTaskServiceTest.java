package com.wjw.stock;

import com.wjw.stock.service.StockTimerTaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StockTimerTaskServiceTest {
    @Autowired
    private StockTimerTaskService stockTimerTaskService;
    @Test
    public void test01() {
        stockTimerTaskService.getInnerMarketInfo();
    }
}
