package com.kill4us.shortlink.project.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 短链接跳转路由实体
 */
@Data
@TableName("t_link_goto")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShortLinkGotoDO {

    private String gid;

    private String fullShortUrl;

    private long id;
}
