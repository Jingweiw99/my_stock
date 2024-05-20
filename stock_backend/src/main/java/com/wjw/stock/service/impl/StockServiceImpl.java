package com.wjw.stock.service.impl;

import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wjw.stock.mapper.StockBlockRtInfoMapper;
import com.wjw.stock.mapper.StockBusinessMapper;
import com.wjw.stock.mapper.StockMarketIndexInfoMapper;
import com.wjw.stock.mapper.StockRtInfoMapper;
import com.wjw.stock.pojo.domain.InnerMarketDomain;
import com.wjw.stock.pojo.domain.StockBlockDomain;
import com.wjw.stock.pojo.domain.StockInfoConfig;
import com.wjw.stock.pojo.domain.StockUpdownDomain;
import com.wjw.stock.pojo.entity.StockBusiness;
import com.wjw.stock.service.StockService;
import com.wjw.stock.utils.DateTimeUtil;
import com.wjw.stock.vo.resp.PageResult;
import com.wjw.stock.vo.resp.R;
import com.wjw.stock.vo.resp.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.*;

@Service("stockService")
@Slf4j
public class StockServiceImpl implements StockService {

    @Autowired
    private StockBlockRtInfoMapper stockBlockRtInfoMapper;

    @Autowired
    private StockMarketIndexInfoMapper stockMarketIndexInfoMapper;
    @Autowired
    private StockRtInfoMapper stockRtInfoMapper;

    @Autowired
    private StockInfoConfig stockInfoConfig;


    /**
     * 获取国内大盘的实时数据
     *
     * @return
     */
    @Override
    public R<List<InnerMarketDomain>> innerIndexAll() {
        //1.获取国内A股大盘的id集合
        List<String> inners = stockInfoConfig.getInner();
        //2.获取最近股票交易日期
        Date lastDate = DateTimeUtil.getLastDate4Stock(DateTime.now()).toDate();
        //TODO: mock测试数据，后期数据通过第三方接口动态获取实时数据 可删除
        lastDate = DateTime.parse("2022-01-02 09:32:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        //3.将获取的java Date传入接口
        List<InnerMarketDomain> list = stockMarketIndexInfoMapper.getMarketInfo(inners, lastDate);
        //4.返回查询结果
        return R.ok(list);
    }


    public R<List<StockBlockDomain>> sectorAll() {

        //获取股票最新交易时间点
        Date lastDate = DateTimeUtil.getLastDate4Stock(DateTime.now()).toDate();
        //TODO mock数据,后续删除
        lastDate = DateTime.parse("2021-12-21 14:30:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        //1.调用mapper接口获取数据
        List<StockBlockDomain> infos = stockBlockRtInfoMapper.sectorAll(lastDate);
        //2.组装数据
        if (CollectionUtils.isEmpty(infos)) {
            return R.error(ResponseCode.NO_RESPONSE_DATA.getMessage());
        }
        return R.ok(infos);
    }

    @Override
    public R<PageResult> getStockPageInfo(Integer page, Integer pageSize) {
        //1.设置PageHelper分页参数
        PageHelper.startPage(page, pageSize);
        //2.获取当前最新的股票交易时间点
        Date curDate = DateTimeUtil.getLastDate4Stock(DateTime.now()).toDate();
        // TODO: mock的数据，后续删除
        curDate = DateTime.parse("2022-06-07 15:00:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        //3.调用mapper接口查询
        List<StockUpdownDomain> infos = stockRtInfoMapper.getNewestStockInfo(curDate);
        if (CollectionUtils.isEmpty(infos)) {
            return R.error(ResponseCode.NO_RESPONSE_DATA);
        }
        //4.组装PageInfo对象，获取分页的具体信息,因为PageInfo包含了丰富的分页信息，而部分分页信息是前端不需要的
        PageResult<StockUpdownDomain> pageResult = new PageResult<>(new PageInfo<>(infos));
        //5.封装响应数据
        return R.ok(pageResult);
    }

    @Override
    public R<Map> getStockUpdownCount() {
        //1.获取最新的交易时间范围 openTime  curTime
        //1.1 获取最新股票交易时间点
        DateTime curDateTime = DateTimeUtil.getLastDate4Stock(DateTime.now());
        Date curTime = curDateTime.toDate();
        //TODO
        curTime = DateTime.parse("2023-12-30 14:25:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        //1.2 获取最新交易时间对应的开盘时间
        DateTime openDate = DateTimeUtil.getOpenDate(curDateTime);
        Date openTime = openDate.toDate();
        openTime = DateTime.parse("2021-12-30 09:30:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        //2.查询涨停数据
        //约定mapper中flag入参： 1-》涨停数据 0：跌停
        List<Map> upCounts = stockRtInfoMapper.getStockUpdownCount(openTime, curTime, 1);
        //3.查询跌停数据
        List<Map> dwCounts = stockRtInfoMapper.getStockUpdownCount(openTime, curTime, 0);
        //4.组装数据
        HashMap<String, List> mapInfo = new HashMap<>();
        mapInfo.put("upList", upCounts);
        mapInfo.put("downList", dwCounts);
        //5.返回结果
        return R.ok(mapInfo);
    }

    @Override
    public void stockExport(HttpServletResponse response, Integer page, Integer pageSize) {
        try {
            //1.获取最近最新的一次股票有效交易时间点（精确分钟）
            Date curDate = DateTimeUtil.getLastDate4Stock(DateTime.now()).toDate();
            //TODO: 后续删掉
            curDate = DateTime.parse("2021-12-30 09:47:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
            //2.设置分页参数
            PageHelper.startPage(page, pageSize);
            //3.查询
            List<StockUpdownDomain> infos = stockRtInfoMapper.getNewestStockInfo(curDate);
            //设置响应excel文件格式类型
            response.setContentType("application/vnd.ms-excel");
            //2.设置响应数据的编码格式
            response.setCharacterEncoding("utf-8");
            //3.设置默认的文件名称
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("stockRt", "UTF-8");
            //设置默认文件名称：兼容一些特殊浏览器
            response.setHeader("content-disposition", "attachment;filename=" + fileName + ".xlsx");
            //4.响应excel流
            EasyExcel
                    .write(response.getOutputStream(), StockUpdownDomain.class)
                    .sheet("股票信息")
                    .doWrite(infos);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("当前导出数据异常，当前页：{},每页大小：{},异常信息：{}", page, pageSize, e.getMessage());
        }
    }

    @Override
    public R<Map<String, List>> getTradeAmt() {
        DateTime lastDateTime = DateTimeUtil.getLastDate4Stock(DateTime.now());
        DateTime openDateTime = DateTimeUtil.getOpenDate(lastDateTime);
        // 转化为java中的Date
        Date startTime4T = openDateTime.toDate();
        Date endTime4T = lastDateTime.toDate();
        // TODO: 这里是mock数据
        startTime4T = DateTime.parse("2022-01-03 09:30:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        endTime4T = DateTime.parse("2022-01-03 14:40:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();

        // 获取T-1日的区间范围
        DateTime preLastDateTime = DateTimeUtil.getPreviousTradingDay(lastDateTime);
        DateTime preOpenDateTime = DateTimeUtil.getOpenDate(preLastDateTime);
        Date startTime4PreT = preOpenDateTime.toDate();
        Date endTime4PreT = preLastDateTime.toDate();
        //TODO  mock数据
        startTime4PreT = DateTime.parse("2022-01-02 09:30:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        endTime4PreT = DateTime.parse("2022-01-02 14:40:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();

        // 获取大盘的id
        List<String> markedIds = stockInfoConfig.getInner();
        // 当天数据和前天交易量数据
        List<Map> data = stockMarketIndexInfoMapper.getTradeAmt(markedIds, startTime4T, endTime4T);
        List<Map> data2 = stockMarketIndexInfoMapper.getTradeAmt(markedIds, startTime4PreT, endTime4PreT);
        Map<String, List> info = new HashMap<>();
        info.put("amtList", data);
        info.put("yesAmtList", data2);
        return R.ok(info);
    }

    @Override
    public R<Map> stockUpDownScopeCount() {
        Date curDate = DateTimeUtil.getLastDate4Stock(DateTime.now()).toDate();
        // TODO: mock data
        curDate = DateTime.parse("2022-01-06 09:55:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:SS")).toDate();
        List<Map> maps = stockRtInfoMapper.stockUpDownScopeCount(curDate);
        HashMap<String, Object> mapInfo = new HashMap<>();
        String curDateStr = curDate.toString();
        List<String> rangeList = stockInfoConfig.getUpDownRange();
        List<Map> mapss = new ArrayList<>();
        for (String s : rangeList) {
            Map info = null;
            for (Map map : maps) {
                if (map.containsValue(s)) {
                    info = map;
                }
                if (info == null) {
                    info = new HashMap();
                    info.put("count", 0);
                    info.put("title", s);
                }
            }
            mapss.add(info);
        }
        mapInfo.put("time", curDateStr);
        mapInfo.put("infos", mapss);
        return R.ok(mapInfo);
    }
}
