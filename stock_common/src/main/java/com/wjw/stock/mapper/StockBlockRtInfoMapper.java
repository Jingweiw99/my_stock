package com.wjw.stock.mapper;

import com.wjw.stock.pojo.domain.StockBlockDomain;
import com.wjw.stock.pojo.entity.StockBlockRtInfo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author 21176
 * @description 针对表【stock_block_rt_info(股票板块详情信息表)】的数据库操作Mapper
 * @createDate 2024-05-15 21:20:39
 * @Entity com.wjw.stock.pojo.entity.StockBlockRtInfo
 */
public interface StockBlockRtInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(StockBlockRtInfo record);

    int insertSelective(StockBlockRtInfo record);

    int insertStockBlock(@Param("lists") List<StockBlockRtInfo> lists);

    StockBlockRtInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StockBlockRtInfo record);

    int updateByPrimaryKey(StockBlockRtInfo record);

    List<StockBlockDomain> sectorAll(@Param("lastDate") Date lastDate);

}
