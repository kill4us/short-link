package com.kill4us.shortlink.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kill4us.shortlink.admin.dao.entity.GroupDO;
import com.kill4us.shortlink.admin.dto.req.ShortLinkGroupSaveReqDTO;
import com.kill4us.shortlink.admin.dto.req.ShortLinkGroupSortReqDTO;
import com.kill4us.shortlink.admin.dto.req.ShortLinkGroupUpdateReqDTO;
import com.kill4us.shortlink.admin.dto.resp.ShortLinkGroupRespDTO;

import java.util.List;

/**
 * 短链接分组接口层
 */
public interface GroupService extends IService<GroupDO> {

    /**
     * 新增短链接分组
     * @param groupName 分组名
     */
    void saveGroup(String groupName);

    /**
     * 查询短链接分组
     * @return
     */
    List<ShortLinkGroupRespDTO> listGroup();

    /**
     * 修改短链接分组
     * @param requestParam 分组名
     */
    void updateGroup(ShortLinkGroupUpdateReqDTO requestParam);

    /**
     * 删除短链接分组
     * @param gid 分组ID
     */
    void deleteGroup(String gid);

    void sortGroup(List<ShortLinkGroupSortReqDTO> requestParam);
}
