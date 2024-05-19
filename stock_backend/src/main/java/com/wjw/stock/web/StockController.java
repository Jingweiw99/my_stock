package com.wjw.stock.web;

import com.wjw.stock.pojo.domain.InnerMarketDomain;
import com.wjw.stock.pojo.domain.StockBlockDomain;
import com.wjw.stock.service.StockService;
import com.wjw.stock.vo.resp.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/quot")
public class StockController {

    @Autowired
    private StockService stockService;

    /**
     * 获取国内最新大盘指数
     * @return
     */
    @GetMapping("/index/all")
    public R<List<InnerMarketDomain>> innerIndexAll(){
        return stockService.innerIndexAll();
    }

    /**
     * 获取沪深两市最新数据，交易总金额降序查询，取前十条数据
     * @return
     */
    @GetMapping("/sector/all")
    public  R<List<StockBlockDomain>> sectorAll() {
        return stockService.sectorAll();
    }
}