package com.wjw.stock.mapper;

import com.wjw.stock.pojo.domain.Stock4EvrDayDomain;
import com.wjw.stock.pojo.domain.Stock4MinuteDomain;
import com.wjw.stock.pojo.domain.StockUpdownDomain;
import com.wjw.stock.pojo.entity.StockRtInfo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* @author 21176
* @description 针对表【stock_rt_info(个股详情信息表)】的数据库操作Mapper
* @createDate 2024-05-15 21:20:39
* @Entity com.wjw.stock.pojo.entity.StockRtInfo
*/
public interface StockRtInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(StockRtInfo record);

    int insertSelective(StockRtInfo record);

    int insertBatch(@Param("lists") List<StockRtInfo> stockRtInfoList);

    StockRtInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StockRtInfo record);

    int updateByPrimaryKey(StockRtInfo record);

    List<StockUpdownDomain> getNewestStockInfo(@Param("curDate") Date curDate);

    List<Map> getStockUpdownCount(@Param("openTime") Date openTime, @Param("curTime") Date curTime, @Param("i") int i);

    List<Map> stockUpDownScopeCount(Date curDate);

    List<Stock4MinuteDomain> getStockInfoByCodeAndDate(@Param("code") String code, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    List<Stock4EvrDayDomain> getStockInfo4EvrDay(@Param("code") String code, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    List<Date> getStockCloseDates(@Param("code") String code, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    List<Stock4EvrDayDomain> getStockInfo4Day2(@Param("code") String code, @Param("dates") List<Date> closeDates);

}
