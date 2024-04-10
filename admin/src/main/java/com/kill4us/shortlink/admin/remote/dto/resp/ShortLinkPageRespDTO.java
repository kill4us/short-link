package com.kill4us.shortlink.admin.remote.dto.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 分页返回
 */
@Data
public class ShortLinkPageRespDTO {

    /**
     * id
     */
    private Long id;

    /**
     * 域名
     */
    private String domain;

    /**
     * 短链接
     */
    private String shortUri;

    /**
     * 完整短链接
     */
    private String fullShortUrl;

    /**
     * 原始链接
     */
    private String originUrl;

    /**
     * 分组标识
     */
    private String gid;

    /**
     * 有效期类型 0：永久有效 1：用户自定义
     */
    private Integer validDateType;

    /**
     * 有效期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date validDate;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 描述
     */
    private String describe;

    /**
     * 网站图标
     */
    private String favicon;

    /**
     * 历史访问uv
     */
    private Integer totalUv;

    /**
     * 今日uv
     */
    private Integer todayUv;

    /**
     * 历史访问pv
     */
    private Integer totalPv;

    /**
     * 今日pv
     */
    private Integer todayPv;

    /**
     * 历史访问ip
     */
    private Integer totalUip;

    /**
     * 今日访问uip
     */
    private Integer todayUip;
}
