package com.wjw.stock.pojo.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 股票板块详情信息表
 * @TableName stock_block_rt_info
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockBlockRtInfo implements Serializable {
    /**
     * 板块主键ID（业务无关）
     */
    private Long id;

    /**
     * 表示，如：new_blhy-玻璃行业
     */
    private String label;

    /**
     * 板块名称
     */
    private String blockName;

    /**
     * 公司数量
     */
    private Integer companyNum;

    /**
     * 平均价格
     */
    private BigDecimal avgPrice;

    /**
     * 涨跌幅
     */
    private BigDecimal updownRate;

    /**
     * 交易量
     */
    private Long tradeAmount;

    /**
     * 交易金额
     */
    private BigDecimal tradeVolume;

    /**
     * 当前日期（精确到秒）
     */
    private Date curTime;

    private static final long serialVersionUID = 1L;
}