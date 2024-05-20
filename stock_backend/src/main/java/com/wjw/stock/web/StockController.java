package com.wjw.stock.web;

import com.wjw.stock.pojo.domain.InnerMarketDomain;
import com.wjw.stock.pojo.domain.StockBlockDomain;
import com.wjw.stock.service.StockService;
import com.wjw.stock.vo.resp.PageResult;
import com.wjw.stock.vo.resp.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/quot")
public class StockController {

    @Autowired
    private StockService stockService;

    /**
     * 获取国内最新大盘指数
     *
     * @return
     */
    @GetMapping("/index/all")
    public R<List<InnerMarketDomain>> innerIndexAll() {
        return stockService.innerIndexAll();
    }

    /**
     * 获取沪深两市最新数据，交易总金额降序查询，取前十条数据
     *
     * @return
     */
    @GetMapping("/sector/all")
    public R<List<StockBlockDomain>> sectorAll() {
        return stockService.sectorAll();
    }

    /**
     * 分页查询股票最新数据，并按照涨幅排序查询
     *
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/stock/all")
    public R<PageResult> getStockPageInfo(@RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
                                          @RequestParam(name = "pageSize", required = false, defaultValue = "20") Integer pageSize) {
        return stockService.getStockPageInfo(page, pageSize);
    }

    @GetMapping("/stock/updown/count")
    public R<Map> getStockUpdownCount(){
        return stockService.getStockUpdownCount();
    }
}