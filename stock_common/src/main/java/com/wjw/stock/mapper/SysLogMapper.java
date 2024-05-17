package com.wjw.stock.mapper;

import com.wjw.stock.pojo.entity.SysLog;

/**
* @author 21176
* @description 针对表【sys_log(系统日志)】的数据库操作Mapper
* @createDate 2024-05-15 21:20:39
* @Entity com.wjw.stock.pojo.entity.SysLog
*/
public interface SysLogMapper {

    int deleteByPrimaryKey(Long id);

    int insert(SysLog record);

    int insertSelective(SysLog record);

    SysLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysLog record);

    int updateByPrimaryKey(SysLog record);

}
