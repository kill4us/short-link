package com.kill4us.shortlink.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kill4us.shortlink.admin.common.convention.result.Result;
import com.kill4us.shortlink.admin.remote.dto.req.ShortLinkRecycleBinPageReqDTO;
import com.kill4us.shortlink.admin.remote.dto.resp.ShortLinkPageRespDTO;

/**
 * url回收站接口层
 */
public interface RecycleBinService {
    /**
     * 分页查询回收站短链接
     * @param requestParam
     * @return
     */
    Result<IPage<ShortLinkPageRespDTO>> pageRecycleBinShortLink(ShortLinkRecycleBinPageReqDTO requestParam);
}
