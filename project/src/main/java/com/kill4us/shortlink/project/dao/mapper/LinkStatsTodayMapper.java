package com.kill4us.shortlink.project.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kill4us.shortlink.project.dao.entity.LinkOSStatsDO;
import com.kill4us.shortlink.project.dao.entity.LinkStatsTodayDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

/**
 * 短链接今日数据统计持久层
 */
public interface LinkStatsTodayMapper extends BaseMapper<LinkStatsTodayDO> {

    /**
     * 记录操作系统访问监控
     */
    @Insert("INSERT INTO t_link_stats_today (full_short_url, gid, date, today_pv, today_uv, today_uip, create_time, update_time, del_flag) " +
            "VALUES( #{linkTodayStats.fullShortUrl}, #{linkTodayStats.gid}, #{linkTodayStats.date}, #{linkTodayStats.todayPv}, #{linkTodayStats.todayUv}, #{linkTodayStats.todayUip}, NOW(), NOW(), 0) ON DUPLICATE KEY UPDATE today_pv = today_pv +  #{linkTodayStats.todayPv}," +
            "today_uv = today_uv + #{linkTodayStats.todayUv}," +
            "today_uip = today_uip + #{linkTodayStats.todayUip}," +
            " update_time = NOW();")
    void shortLinkTodayState(@Param("linkTodayStats") LinkStatsTodayDO linkStatsTodayDO);
}
