package com.kill4us.shortlink.project.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kill4us.shortlink.project.dto.req.ShortLinkStatsAccessRecordReqDTO;
import com.kill4us.shortlink.project.dto.req.ShortLinkStatsReqDTO;
import com.kill4us.shortlink.project.dto.resp.ShortLinkStatsAccessRecordRespDTO;
import com.kill4us.shortlink.project.dto.resp.ShortLinkStatsRespDTO;

/**
 * 短链接监控接口层
 */
public interface ShortLinkStatsService {

    /**
     * 获取单个短链接监控数据
     */
    ShortLinkStatsRespDTO oneShortLinkStats(ShortLinkStatsReqDTO requestParam);

    /**
     * 访问单个短链接指定时间内访问记录监控数据
     */
    IPage<ShortLinkStatsAccessRecordRespDTO> shortLinkStatsAccessRecord(ShortLinkStatsAccessRecordReqDTO requestParam);
}