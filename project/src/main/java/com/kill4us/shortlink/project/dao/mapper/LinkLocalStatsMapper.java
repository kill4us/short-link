package com.kill4us.shortlink.project.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kill4us.shortlink.project.dao.entity.LinkLocalStatsDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

public interface LinkLocalStatsMapper extends BaseMapper<LinkLocalStatsDO> {

    /**
     * 记录地区访问监控
     */
    @Insert("INSERT INTO t_link_locale_stats (full_short_url, gid, date, cnt, country, city, adcode, province, create_time, update_time, del_flag) " +
            "VALUES( #{linkLocalStats.fullShortUrl}, #{linkLocalStats.gid}, #{linkLocalStats.date}, #{linkLocalStats.cnt}, #{linkLocalStats.country}, #{linkLocalStats.city}, #{linkLocalStats.adcode}, #{linkLocalStats.province}, NOW(), NOW(), 0) ON DUPLICATE KEY UPDATE cnt = cnt +  #{linkLocalStats.cnt};")
    void shortLinkLocalState(@Param("linkLocalStats") LinkLocalStatsDO linkLocalStatsDO);
}
