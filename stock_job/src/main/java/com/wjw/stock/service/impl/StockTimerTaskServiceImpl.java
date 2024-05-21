package com.wjw.stock.service.impl;

import com.google.common.collect.Lists;
import com.wjw.stock.constant.ParseType;
import com.wjw.stock.mapper.StockBlockRtInfoMapper;
import com.wjw.stock.mapper.StockBusinessMapper;
import com.wjw.stock.mapper.StockMarketIndexInfoMapper;
import com.wjw.stock.mapper.StockRtInfoMapper;
import com.wjw.stock.pojo.domain.StockInfoConfig;
import com.wjw.stock.pojo.entity.StockBlockRtInfo;
import com.wjw.stock.pojo.entity.StockBusiness;
import com.wjw.stock.pojo.entity.StockMarketIndexInfo;
import com.wjw.stock.pojo.entity.StockRtInfo;
import com.wjw.stock.service.StockTimerTaskService;
import com.wjw.stock.utils.DateTimeUtil;
import com.wjw.stock.utils.IdWorker;
import com.wjw.stock.utils.ParserStockInfoUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service("stockTimerTaskService")
@Slf4j
public class StockTimerTaskServiceImpl implements StockTimerTaskService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private StockInfoConfig stockInfoConfig;

    @Autowired
    private IdWorker idWorker;
    @Autowired
    private StockMarketIndexInfoMapper stockMarketIndexInfoMapper;
    @Autowired
    private ParserStockInfoUtil parserStockInfoUtil;
    @Autowired
    private StockBusinessMapper stockBusinessMapper;
    @Autowired
    private StockRtInfoMapper stockRtInfoMapper;
    @Autowired
    private StockBlockRtInfoMapper stockBlockRtInfoMapper;
    @Override
    public void getInnerMarketInfo() {
        //1.定义采集的url接口
        String url = stockInfoConfig.getMarketUrl() + String.join(",", stockInfoConfig.getInner());
        //2.调用restTemplate采集数据
        //2.1 组装请求头
        HttpHeaders headers = new HttpHeaders();
        //必须填写，否则数据采集不到(添加防盗链)
        headers.add("Referer", "https://finance.sina.com.cn/stock/");
        headers.add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36");
        //2.2 组装请求对象
        HttpEntity<Object> entity = new HttpEntity<>(headers);
        //2.3 resetTemplate发起请求
        String resString = restTemplate.postForObject(url, entity, String.class);
        //log.info("当前采集的数据：{}",resString);
        List list = parserStockInfoUtil.parser4StockOrMarketInfo(resString, ParseType.INNER);
        log.info("采集的当前大盘数据：{}", list);
        //批量插入
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        // 批量插入功能
        int count = this.stockMarketIndexInfoMapper.insertBatch(list);
        log.info("批量插入了：{}条数据", count);
    }

    /**
     * 批量获取股票分时数据详情信息
     * http://hq.sinajs.cn/list=sz000002,sh600015
     */
    @Override
    public void getStockRtIndex() {
        //批量获取股票ID集合
        List<String> stockIds = stockBusinessMapper.getStockIds();
        //计算出符合sina命名规范的股票id数据
        stockIds = stockIds.stream().map(id -> {
            return id.startsWith("6") ? "sh" + id : "sz" + id;
        }).collect(Collectors.toList());
        //设置公共请求头对象 设置请求头数据
        HttpHeaders headers = new HttpHeaders();
        headers.add("Referer", "https://finance.sina.com.cn/stock/");
        headers.add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36");
        HttpEntity<String> entity = new HttpEntity<>(headers);
        //一次性查询过多，我们将需要查询的数据先进行分片处理，每次最多查询20条股票数据
        Lists.partition(stockIds, 20).forEach(list -> {
            //拼接股票url地址
            String stockUrl = stockInfoConfig.getMarketUrl() + String.join(",", list);
            //获取响应数据
            String result = restTemplate.postForObject(stockUrl, entity, String.class);
            List<StockRtInfo> infos = parserStockInfoUtil.parser4StockOrMarketInfo(result, ParseType.ASHARE);
            log.info("数据量：{}", infos.size());
            // 批量插入
            int i = stockRtInfoMapper.insertBatch(infos);
            if (i <= 0) {
                log.info("插入失败:影响{}行数据", i);
                return;
            }
            log.info("插入成功:影响{}行数据", i);
        });
    }

    @Override
    public void getBlockInfo() {
        HttpHeaders header = new HttpHeaders();
        header.add("Referer", "https://finance.sina.com.cn/stock/");
        HttpEntity entity = new HttpEntity(header);
        String blockUrl = stockInfoConfig.getBlockUrl();
        String strInfo = restTemplate.postForObject(blockUrl, entity, String.class);
        log.info("获取板块行业数据的str:{}", strInfo);
        // 穿过来的是一个类似js的对象
        List<StockBlockRtInfo> lists = parserStockInfoUtil.parse4StockBlock(strInfo);
        int i = stockBlockRtInfoMapper.insertStockBlock(lists);
        if(i == 0) {
            log.info("插入失败");
        } else {
            log.info("插入成功");
        }
    }
}
