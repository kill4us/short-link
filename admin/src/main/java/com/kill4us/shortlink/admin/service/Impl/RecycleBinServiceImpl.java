package com.kill4us.shortlink.admin.service.Impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.kill4us.shortlink.admin.common.biz.user.UserContext;
import com.kill4us.shortlink.admin.common.convention.exception.ClientException;
import com.kill4us.shortlink.admin.common.convention.exception.ServiceException;
import com.kill4us.shortlink.admin.common.convention.result.Result;
import com.kill4us.shortlink.admin.dao.entity.GroupDO;
import com.kill4us.shortlink.admin.dao.mapper.GroupMapper;
import com.kill4us.shortlink.admin.remote.ShortLinkRemoteService;
import com.kill4us.shortlink.admin.remote.dto.req.ShortLinkPageReqDTO;
import com.kill4us.shortlink.admin.remote.dto.req.ShortLinkRecycleBinPageReqDTO;
import com.kill4us.shortlink.admin.remote.dto.resp.ShortLinkPageRespDTO;
import com.kill4us.shortlink.admin.service.RecycleBinService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * url回收站接口实现层
 */
@Service
@RequiredArgsConstructor
public class RecycleBinServiceImpl implements RecycleBinService {

    private final GroupMapper groupMapper;

    ShortLinkRemoteService shortLinkRemoteService = new ShortLinkRemoteService() {
    };

    @Override
    public Result<IPage<ShortLinkPageRespDTO>> pageRecycleBinShortLink(ShortLinkRecycleBinPageReqDTO requestParam) {
        LambdaQueryWrapper<GroupDO> queryWrapper = Wrappers.lambdaQuery(GroupDO.class)
                .eq(GroupDO::getUsername, UserContext.getUsername())
                .eq(GroupDO::getDelFlag, 0);
        List<GroupDO> groupDOList = groupMapper.selectList(queryWrapper);
        if (CollUtil.isEmpty(groupDOList)) {
            throw new ServiceException("用户无分组信息");
        }
        requestParam.setGidList(groupDOList.stream().map(GroupDO::getGid).toList());
        return shortLinkRemoteService.pageRecycleBinShortLink(requestParam);
    }
}
