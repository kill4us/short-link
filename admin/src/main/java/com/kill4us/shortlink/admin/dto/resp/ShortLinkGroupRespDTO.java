package com.kill4us.shortlink.admin.dto.resp;

import lombok.Data;

/**
 * 短链接返回实体对象
 */
@Data
public class ShortLinkGroupRespDTO {

    /**
     * 分组标识
     */
    private String gid;

    /**
     * 分组名称
     */
    private String name;

    /**
     * 分组排序
     */
    private Integer sortOrder;

    /**
     * 当前分组下短链接的数量
     */
    private Integer shortLinkCount;
}
