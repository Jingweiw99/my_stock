package com.wjw.stock.mapper;

import com.wjw.stock.pojo.domain.InnerMarketDomain;
import com.wjw.stock.pojo.entity.StockMarketIndexInfo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* @author 21176
* @description 针对表【stock_market_index_info(国内大盘数据详情表)】的数据库操作Mapper
* @createDate 2024-05-15 21:20:39
* @Entity com.wjw.stock.pojo.entity.StockMarketIndexInfo
*/
public interface StockMarketIndexInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(StockMarketIndexInfo record);

    int insertSelective(StockMarketIndexInfo record);

    StockMarketIndexInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StockMarketIndexInfo record);

    int updateByPrimaryKey(StockMarketIndexInfo record);

    List<InnerMarketDomain> getMarketInfo(@Param("inners") List<String> inners, @Param("lastDate") Date lastDate);

    List<Map> getTradeAmt(@Param("markedIds") List<String> markedIds, @Param("startDate") Date startTime4PreT, @Param("endDate") Date endTime4PreT);
    /**
     * 批量插入股票大盘数据
     * @param infos
     */
    int insertBatch(@Param("infos") List<StockMarketIndexInfo> infos);
}
