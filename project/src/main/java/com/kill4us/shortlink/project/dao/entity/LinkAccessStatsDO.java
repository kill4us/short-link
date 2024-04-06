package com.kill4us.shortlink.project.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.kill4us.shortlink.project.common.database.baseDO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 短链接监控实体层
 */
@Data
@TableName("t_link_access_stats")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LinkAccessStatsDO extends baseDO {

    /**
     * id
     */
    private Long id;

    /**
     * 分组标识
     */
    private String gid;

    /**
     * 完整短链接
     */
    private String fullShortUrl;

    /**
     * 日期
     */
    private Date date;

    /**
     * 访问量
     */
    private Integer pv;

    /**
     * 独立访问数
     */
    private Integer uv;

    /**
     * 独立ip数
     */
    private Integer uip;

    /**
     * 小时
     */
    private Integer hour;

    /**
     * 星期
     */
    private Integer weekday;

}