package com.kill4us.shortlink.project.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kill4us.shortlink.project.dao.entity.ShortLinkDO;
import com.kill4us.shortlink.project.dto.req.*;
import com.kill4us.shortlink.project.dto.resp.ShortLinkPageRespDTO;

/**
 * 短链接回收站接口层
 */
public interface RecycleBinService extends IService<ShortLinkDO> {

    /**
     * 保存短链接回收站
     * @param requestParam
     */
    void saveRecycleBin(RecycleBinSaveReqDTO requestParam);

    /**
     * 分页查询回收站短链接
     * @param requestParam 分页查询短链接请求参数
     * @return 分页结果
     */
    IPage<ShortLinkPageRespDTO> pageRecycleBinShortLink(ShortLinkRecycleBinPageReqDTO requestParam);

    /**
     * 从回收站中恢复短链接
     */
    void recoverFromCycleBin(RecycleBinRecoverReqDTO requestParam);

    /**
     * 从回收站彻底删除短链接
     */
    void removeFromCycleBin(RecycleBinRemoveReqDTO requestParam);
}
