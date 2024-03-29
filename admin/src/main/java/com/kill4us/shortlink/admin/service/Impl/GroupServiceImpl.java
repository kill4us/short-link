package com.kill4us.shortlink.admin.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.BeanUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kill4us.shortlink.admin.common.biz.user.UserContext;
import com.kill4us.shortlink.admin.dao.entity.GroupDO;
import com.kill4us.shortlink.admin.dao.mapper.GroupMapper;
import com.kill4us.shortlink.admin.dto.req.ShortLinkGroupSaveReqDTO;
import com.kill4us.shortlink.admin.dto.req.ShortLinkGroupUpdateReqDTO;
import com.kill4us.shortlink.admin.dto.resp.ShortLinkGroupRespDTO;
import com.kill4us.shortlink.admin.service.GroupService;
import com.kill4us.shortlink.admin.utils.RandomGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

/**
 * 短链接分组接口实现层
 */
@Service
@Slf4j
public class GroupServiceImpl extends ServiceImpl<GroupMapper, GroupDO> implements GroupService {

    /**
     * 新增短链接分组
     * @param groupName 分组名
     */
    @Override
    public void saveGroup(String groupName) {
        String gid;
        while (true) {
            gid = RandomGenerator.generateRandom();
            if (hasGid(gid)) {
                break;
            }
        }
        GroupDO groupDO = GroupDO.builder()
                .name(groupName)
                .username(UserContext.getUsername())
                .sortOrder(0)
                .gid(gid)
                .build();
        baseMapper.insert(groupDO);
    }

    /**
     * 检查gid是否可用
     * @param gid
     * @return 可用返回true
     */
    private boolean hasGid(String gid) {
        LambdaQueryWrapper<GroupDO> queryWrapper = Wrappers.lambdaQuery(GroupDO.class)
                .eq(GroupDO::getGid, gid)
                .eq(GroupDO::getUsername, UserContext.getUsername());
        GroupDO hasGroup = baseMapper.selectOne(queryWrapper);
        return hasGroup == null;
    }

    /**
     * 查询短链接分组
     * @return List集合
     */
    @Override
    public List<ShortLinkGroupRespDTO> listGroup() {
        LambdaQueryWrapper<GroupDO> queryWrapper = Wrappers.lambdaQuery(GroupDO.class)
                .eq(GroupDO::getDelFlag, 0)
                .orderByDesc(GroupDO::getSortOrder, GroupDO::getUpdateTime)
                .eq(GroupDO::getUsername, UserContext.getUsername());
        List<GroupDO> groupDOList = baseMapper.selectList(queryWrapper);
        return BeanUtil.copyToList(groupDOList, ShortLinkGroupRespDTO.class);
    }

    @Override
    public void updateGroup(ShortLinkGroupUpdateReqDTO requestParam) {
        LambdaUpdateWrapper<GroupDO> updateWrapper = Wrappers.lambdaUpdate(GroupDO.class)
                .eq(GroupDO::getUsername, UserContext.getUsername())
                .eq(GroupDO::getGid, requestParam.getGid())
                .eq(GroupDO::getDelFlag, 0);
        GroupDO groupDO = new GroupDO();
        groupDO.setName(requestParam.getName());
        baseMapper.update(groupDO, updateWrapper);
    }
}
