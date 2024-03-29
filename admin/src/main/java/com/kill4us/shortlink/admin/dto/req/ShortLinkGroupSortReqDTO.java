package com.kill4us.shortlink.admin.dto.req;

import lombok.Data;

/**
 * 短链接排序分组请求参数
 */
@Data
public class ShortLinkGroupSortReqDTO {

    private Integer sortOrder;
    private String gid;
}
