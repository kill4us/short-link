package com.kill4us.shortlink.admin.dto.req;

import lombok.Data;

/**
 * 短链接新增分组请求参数
 */
@Data
public class ShortLinkGroupUpdateReqDTO {
    private String gid;
    private String name;
}
