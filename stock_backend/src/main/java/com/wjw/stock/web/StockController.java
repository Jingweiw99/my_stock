package com.wjw.stock.web;

import com.wjw.stock.pojo.domain.InnerMarketDomain;
import com.wjw.stock.pojo.domain.Stock4MinuteDomain;
import com.wjw.stock.pojo.domain.StockBlockDomain;
import com.wjw.stock.service.StockService;
import com.wjw.stock.vo.resp.PageResult;
import com.wjw.stock.vo.resp.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
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
    public R<Map> getStockUpdownCount() {
        return stockService.getStockUpdownCount();
    }

    /**
     * 将指定页的股票数据导出到excel表下
     *
     * @param response
     * @param page     当前页
     * @param pageSize 每页大小
     */
    @GetMapping("/stock/export")
    public void stockExport(HttpServletResponse response, @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
                            @RequestParam(name = "pageSize", required = false, defaultValue = "20") Integer pageSize) {
        stockService.stockExport(response, page, pageSize);
    }
    @GetMapping("/stock/tradeAmt")
    public R<Map<String, List>> getTradeAmt() {
        return stockService.getTradeAmt();
    }

    /**
     * 查询当前时间下股票的涨跌幅度区间统计功能
     * @return
     */
    @GetMapping("/stock/updown")
    public R<Map> getStockUpDown(){
        return stockService.stockUpDownScopeCount();
    }

    /**
     * 功能描述：查询单个个股的分时行情数据，也就是统计指定股票T日每分钟的交易数据；
     *         如果当前日期不在有效时间内，则以最近的一个股票交易时间作为查询时间点
     * @param code 股票编码
     * @return
     */
    @GetMapping("/stock/screen/time-sharing")
    public R<List<Stock4MinuteDomain>> stockScreenTimeSharing(String code){
        return stockService.stockScreenTimeSharing(code);
    }
}