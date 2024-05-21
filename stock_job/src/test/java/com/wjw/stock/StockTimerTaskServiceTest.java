package com.wjw.stock;

import com.wjw.stock.mapper.StockBusinessMapper;
import com.wjw.stock.pojo.entity.StockBusiness;
import com.wjw.stock.service.StockTimerTaskService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
public class StockTimerTaskServiceTest {
    @Autowired
    private StockTimerTaskService stockTimerTaskService;
    @Autowired
    private StockBusinessMapper stockBusinessMapper;
    @Test
    public void test01() {
        stockTimerTaskService.getInnerMarketInfo();
    }

    @Test
    public void test02() {
        stockTimerTaskService.getStockRtIndex();
    }
}
