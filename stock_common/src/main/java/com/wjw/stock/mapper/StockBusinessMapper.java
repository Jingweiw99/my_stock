package com.wjw.stock.mapper;

import com.wjw.stock.pojo.entity.StockBusiness;

import java.util.List;

/**
* @author 21176
* @description 针对表【stock_business(主营业务表)】的数据库操作Mapper
* @createDate 2024-05-15 21:20:39
* @Entity com.wjw.stock.pojo.entity.StockBusiness
*/
public interface StockBusinessMapper {

    int deleteByPrimaryKey(Long id);

    int insert(StockBusiness record);

    int insertSelective(StockBusiness record);

    StockBusiness selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StockBusiness record);

    int updateByPrimaryKey(StockBusiness record);

//    List<StockBusiness> findAll();
}
