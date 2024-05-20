package com.wjw.stock.service;

import com.wjw.stock.pojo.domain.InnerMarketDomain;
import com.wjw.stock.pojo.domain.Stock4EvrDayDomain;
import com.wjw.stock.pojo.domain.Stock4MinuteDomain;
import com.wjw.stock.pojo.domain.StockBlockDomain;
import com.wjw.stock.vo.resp.PageResult;
import com.wjw.stock.vo.resp.R;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface StockService {
    R<List<InnerMarketDomain>> innerIndexAll();

    R<List<StockBlockDomain>> sectorAll();

    R<PageResult> getStockPageInfo(Integer page, Integer pageSize);

    R<Map> getStockUpdownCount();

    void stockExport(HttpServletResponse response, Integer page, Integer pageSize);

    R<Map<String, List>> getTradeAmt();

    R<Map> stockUpDownScopeCount();

    R<List<Stock4MinuteDomain>> stockScreenTimeSharing(String code);

    R<List<Stock4EvrDayDomain>> stockCreenDkLine(String stockCode);
}
