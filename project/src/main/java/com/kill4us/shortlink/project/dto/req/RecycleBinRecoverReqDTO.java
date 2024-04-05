package com.kill4us.shortlink.project.dto.req;

import lombok.Data;

/**
 * 回收站恢复请求参数
 */
@Data
public class RecycleBinRecoverReqDTO {

    /**
     * 分组标识
     */
    private String gid;

    /**
     * 短链接
     */
    private String fullShortUrl;
}
