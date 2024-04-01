package com.kill4us.shortlink.project.dto.resp;

import lombok.Data;

/**
 * 短链接分组内数量查询返回参数
 */
@Data
public class ShortLinkCountQueryRespDTO {

    /**
     * 分组标识
     */
    private String gid;

    /**
     * 数量
     */
    private Integer shortLinkCount;
}
