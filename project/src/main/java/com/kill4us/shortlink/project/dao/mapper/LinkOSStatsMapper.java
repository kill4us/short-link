package com.kill4us.shortlink.project.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kill4us.shortlink.project.dao.entity.LinkLocalStatsDO;
import com.kill4us.shortlink.project.dao.entity.LinkOSStatsDO;
import com.kill4us.shortlink.project.dto.req.ShortLinkStatsReqDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.HashMap;
import java.util.List;

public interface LinkOSStatsMapper extends BaseMapper<LinkOSStatsDO> {

    /**
     * 记录操作系统访问监控
     */
    @Insert("INSERT INTO t_link_os_stats (full_short_url, gid, date, cnt, os, create_time, update_time, del_flag) " +
            "VALUES( #{linkOSStats.fullShortUrl}, #{linkOSStats.gid}, #{linkOSStats.date}, #{linkOSStats.cnt}, #{linkOSStats.os}, NOW(), NOW(), 0) ON DUPLICATE KEY UPDATE cnt = cnt +  #{linkOSStats.cnt}," +
            " update_time = NOW();")
    void shortLinkOSState(@Param("linkOSStats") LinkOSStatsDO linkOSStatsDO);

    /**
     * 根据短链接获取指定日期内操作系统监控数据
     */
    @Select("SELECT " +
            "    os, " +
            "    SUM(cnt) AS count " +
            "FROM " +
            "    t_link_os_stats " +
            "WHERE " +
            "    full_short_url = #{param.fullShortUrl} " +
            "    AND gid = #{param.gid} " +
            "    AND date BETWEEN #{param.startDate} and #{param.endDate} " +
            "GROUP BY " +
            "    full_short_url, gid, os;")
    List<HashMap<String, Object>> listOsStatsByShortLink(@Param("param") ShortLinkStatsReqDTO requestParam);
}
