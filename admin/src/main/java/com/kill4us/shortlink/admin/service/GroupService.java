package com.kill4us.shortlink.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kill4us.shortlink.admin.dao.entity.GroupDO;

/**
 * 短链接分组接口层
 */
public interface GroupService extends IService<GroupDO> {

    /**
     * 新增短链接分组
     * @param groupName 分组名
     */
    void saveGroup(String groupName);
}
