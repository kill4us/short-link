package com.kill4us.shortlink.project.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kill4us.shortlink.project.dao.entity.LinkLocalStatsDO;
import com.kill4us.shortlink.project.dto.req.ShortLinkStatsReqDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface LinkLocalStatsMapper extends BaseMapper<LinkLocalStatsDO> {

    /**
     * 记录地区访问监控
     */
    @Insert("INSERT INTO t_link_locale_stats (full_short_url, gid, date, cnt, country, city, adcode, province, create_time, update_time, del_flag) " +
            "VALUES( #{linkLocalStats.fullShortUrl}, #{linkLocalStats.gid}, #{linkLocalStats.date}, #{linkLocalStats.cnt}, #{linkLocalStats.country}, #{linkLocalStats.city}, #{linkLocalStats.adcode}, #{linkLocalStats.province}, NOW(), NOW(), 0) ON DUPLICATE KEY UPDATE cnt = cnt +  #{linkLocalStats.cnt}," +
            "update_time = NOW();" )
    void shortLinkLocalState(@Param("linkLocalStats") LinkLocalStatsDO linkLocalStatsDO);

    /**
     * 根据短链接获取指定日期内基础监控数据
     */
    @Select("SELECT " +
            "    province, " +
            "    SUM(cnt) AS cnt " +
            "FROM " +
            "    t_link_locale_stats " +
            "WHERE " +
            "    full_short_url = #{param.fullShortUrl} " +
            "    AND gid = #{param.gid} " +
            "    AND date BETWEEN #{param.startDate} and #{param.endDate} " +
            "GROUP BY " +
            "    full_short_url, gid, province;")
    List<LinkLocalStatsDO> listLocaleByShortLink(@Param("param") ShortLinkStatsReqDTO requestParam);
}
