package com.wjw.stock.service.impl;

import com.wjw.stock.pojo.domain.StockInfoConfig;
import com.wjw.stock.pojo.entity.StockMarketIndexInfo;
import com.wjw.stock.service.StockTimerTaskService;
import com.wjw.stock.utils.DateTimeUtil;
import com.wjw.stock.utils.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("stockTimerTaskService")
@Slf4j
public class StockTimerTaskServiceImpl implements StockTimerTaskService {

        @Autowired
        private RestTemplate restTemplate;

        @Autowired
        private StockInfoConfig stockInfoConfig;

        @Autowired
        private IdWorker idWorker;

        @Override
        public void getInnerMarketInfo() {
            //1.定义采集的url接口
            String url=stockInfoConfig.getMarketUrl() + String.join(",",stockInfoConfig.getInner());
            //2.调用restTemplate采集数据
            //2.1 组装请求头
            HttpHeaders headers = new HttpHeaders();
            //必须填写，否则数据采集不到(添加防盗链)
            headers.add("Referer","https://finance.sina.com.cn/stock/");
            headers.add("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36");
            //2.2 组装请求对象
            HttpEntity<Object> entity = new HttpEntity<>(headers);
            //2.3 resetTemplate发起请求
            String resString = restTemplate.postForObject(url, entity, String.class);
            //log.info("当前采集的数据：{}",resString);
            //3.数据解析
            String reg="var hq_str_(.+)=\"(.+)\";";
            //编译表达式,获取编译对象
            Pattern pattern = Pattern.compile(reg);
            //匹配字符串
            Matcher matcher = pattern.matcher(resString);
            ArrayList<StockMarketIndexInfo> list = new ArrayList<>();
            //判断是否有匹配的数值
            while (matcher.find()){
                //获取大盘的code
                String marketCode = matcher.group(1);
                //获取其它信息，字符串以逗号间隔
                String otherInfo=matcher.group(2);
                //以逗号切割字符串，形成数组
                String[] splitArr = otherInfo.split(",");
                //大盘名称
                String marketName=splitArr[0];
                //获取当前大盘的开盘点数
                BigDecimal openPoint=new BigDecimal(splitArr[1]);
                //前收盘点
                BigDecimal preClosePoint=new BigDecimal(splitArr[2]);
                //获取大盘的当前点数
                BigDecimal curPoint=new BigDecimal(splitArr[3]);
                //获取大盘最高点
                BigDecimal maxPoint=new BigDecimal(splitArr[4]);
                //获取大盘的最低点
                BigDecimal minPoint=new BigDecimal(splitArr[5]);
                //获取成交量
                Long tradeAmt=Long.valueOf(splitArr[8]);
                //获取成交金额
                BigDecimal tradeVol=new BigDecimal(splitArr[9]);
                //时间
                Date curTime = DateTimeUtil.getDateTimeWithoutSecond(splitArr[30] + " " + splitArr[31]).toDate();
                //组装entity对象
                StockMarketIndexInfo info = StockMarketIndexInfo.builder()
                        .id(idWorker.nextId())
                        .marketCode(marketCode)
                        .marketName(marketName)
                        .curPoint(curPoint)
                        .openPoint(openPoint)
                        .preClosePoint(preClosePoint)
                        .maxPoint(maxPoint)
                        .minPoint(minPoint)
                        .tradeVolume(tradeVol)
                        .tradeAmount(tradeAmt)
                        .curTime(curTime)
                        .build();
                //收集封装的对象，方便批量插入
                list.add(info);
            }
            log.info("采集的当前大盘数据：{}",list);
            //批量插入
            if (CollectionUtils.isEmpty(list)) {
                return;
            }
            //TODO 后续完成批量插入功能
        }
}