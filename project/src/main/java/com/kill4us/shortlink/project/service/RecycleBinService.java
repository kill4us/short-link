package com.kill4us.shortlink.project.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.kill4us.shortlink.project.dao.entity.ShortLinkDO;
import com.kill4us.shortlink.project.dto.req.RecycleBinSaveReqDTO;

/**
 * 短链接回收站接口层
 */
public interface RecycleBinService extends IService<ShortLinkDO> {

    /**
     * 保存短链接回收站
     * @param requestParam
     */
    void saveRecycleBin(RecycleBinSaveReqDTO requestParam);
}
