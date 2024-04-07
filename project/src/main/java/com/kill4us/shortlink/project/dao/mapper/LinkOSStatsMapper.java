package com.kill4us.shortlink.project.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kill4us.shortlink.project.dao.entity.LinkLocalStatsDO;
import com.kill4us.shortlink.project.dao.entity.LinkOSStatsDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

public interface LinkOSStatsMapper extends BaseMapper<LinkOSStatsDO> {

    /**
     * 记录操作系统访问监控
     */
    @Insert("INSERT INTO t_link_os_stats (full_short_url, gid, date, cnt, os, create_time, update_time, del_flag) " +
            "VALUES( #{linkOSStats.fullShortUrl}, #{linkOSStats.gid}, #{linkOSStats.date}, #{linkOSStats.cnt}, #{linkOSStats.os}, NOW(), NOW(), 0) ON DUPLICATE KEY UPDATE cnt = cnt +  #{linkOSStats.cnt}," +
            " update_time = NOW();")
    void shortLinkOSState(@Param("linkOSStats") LinkOSStatsDO linkOSStatsDO);
}
