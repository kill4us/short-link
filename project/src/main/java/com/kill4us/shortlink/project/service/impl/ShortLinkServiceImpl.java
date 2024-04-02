package com.kill4us.shortlink.project.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.http.server.HttpServerRequest;
import cn.hutool.http.server.HttpServerResponse;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kill4us.shortlink.project.common.convention.exception.ServiceException;
import com.kill4us.shortlink.project.common.enums.ValidDateTypeEnum;
import com.kill4us.shortlink.project.dao.entity.ShortLinkDO;
import com.kill4us.shortlink.project.dao.entity.ShortLinkGotoDO;
import com.kill4us.shortlink.project.dao.mapper.ShortLinkGotoMapper;
import com.kill4us.shortlink.project.dao.mapper.ShortLinkMapper;
import com.kill4us.shortlink.project.dto.req.ShortLinkCreateReqDTO;
import com.kill4us.shortlink.project.dto.req.ShortLinkPageReqDTO;
import com.kill4us.shortlink.project.dto.req.ShortLinkUpdateReqDTO;
import com.kill4us.shortlink.project.dto.resp.ShortLinkCountQueryRespDTO;
import com.kill4us.shortlink.project.dto.resp.ShortLinkCreateRespDTO;
import com.kill4us.shortlink.project.dto.resp.ShortLinkPageRespDTO;
import com.kill4us.shortlink.project.service.ShortLinkService;
import com.kill4us.shortlink.project.utils.HashUtil;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBloomFilter;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 短链接接口实现层
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ShortLinkServiceImpl extends ServiceImpl<ShortLinkMapper, ShortLinkDO> implements ShortLinkService {

    private final RBloomFilter<String> shortLinkCreateCachePenetrationBloomFilter;
    private final ShortLinkGotoMapper shortLinkGotoMapper;

    /**
     * 短链接创建
     * @param requestParam 创建短链接请求参数
     * @return 短链接返回实体
     */
    @Override
    public ShortLinkCreateRespDTO createShortLink(ShortLinkCreateReqDTO requestParam) {
        String shortLinkSuffix = generateSuffix(requestParam);
        ShortLinkDO shortLinkDO = BeanUtil.toBean(requestParam, ShortLinkDO.class);
        shortLinkDO.setFullShortUrl(requestParam.getDomain() + "/" + shortLinkSuffix);
        String fullShortUrl = requestParam.getDomain() + "/" + shortLinkSuffix;
        shortLinkDO.setShortUri(shortLinkSuffix);
        shortLinkDO.setEnableStatus(0);

        ShortLinkGotoDO shortLinkGotoDO = ShortLinkGotoDO.builder()
                .fullShortUrl(fullShortUrl)
                .gid(requestParam.getGid())
                .build();
        try {
            baseMapper.insert(shortLinkDO);
            shortLinkGotoMapper.insert(shortLinkGotoDO);
        } catch (DuplicateKeyException ex) {
            log.warn("短链接 {} 重复入库", fullShortUrl);
            throw new ServiceException("短链接重复生成，请重试");
        }
        shortLinkCreateCachePenetrationBloomFilter.add(fullShortUrl);
        return ShortLinkCreateRespDTO.builder()
                .fullShortUrl(requestParam.getDomainProtocol() + shortLinkDO.getFullShortUrl())
                .originUrl(requestParam.getOriginUrl())
                .gid(requestParam.getGid())
                .build();
    }

    /**
     * 根据原始链接生成短链接后缀
     * @param requestParam  原始链接
     * @return 短链接
     */
    private String generateSuffix(ShortLinkCreateReqDTO requestParam) {
        int customGenerateCount = 0;
        String shortUri;
        while (true) {
            if (customGenerateCount > 10) {
                throw new ServiceException("短链接频繁生成，请重试");
            }
            String originUrl = requestParam.getOriginUrl();
            originUrl += System.currentTimeMillis();
            shortUri = HashUtil.hashToBase62(originUrl);
            if (!shortLinkCreateCachePenetrationBloomFilter.contains(requestParam.getDomain() + "/" + shortUri)) {
                break;
            }
            customGenerateCount ++;
        }
        return shortUri;
    }

    /**
     * 短链接分页查询
     * @param requestParam 分页查询短链接请求参数
     */
    @Override
    public IPage<ShortLinkPageRespDTO> pageShortLink(ShortLinkPageReqDTO requestParam) {
        LambdaQueryWrapper<ShortLinkDO> queryWrapper = Wrappers.lambdaQuery(ShortLinkDO.class)
                .eq(ShortLinkDO::getGid, requestParam.getGid())
                .eq(ShortLinkDO::getEnableStatus, 0)
                .eq(ShortLinkDO::getDelFlag, 0);
        IPage<ShortLinkDO> resultPage = baseMapper.selectPage(requestParam, queryWrapper);
        return resultPage.convert(each -> BeanUtil.toBean(each, ShortLinkPageRespDTO.class));
    }

    @Override
    public List<ShortLinkCountQueryRespDTO> listGroupShortLinkCount(List<String> requestParam) {
        QueryWrapper<ShortLinkDO> queryWrapper = Wrappers.query(new ShortLinkDO()).select("gid, count(*) as shortLinkCount")
                .in("gid", requestParam)
                .eq("enable_status", 0)
                .groupBy("gid");
        List<Map<String, Object>> shortLinkDOS = baseMapper.selectMaps(queryWrapper);
        return BeanUtil.copyToList(shortLinkDOS, ShortLinkCountQueryRespDTO.class);
    }

    /**
     * 更新短链接信息
     * @param requestParam
     */
    @Override
    public void updateShortLink(ShortLinkUpdateReqDTO requestParam) {
        //  Todo 待实现

    }

    @SneakyThrows
    @Override
    public void restoreUrl(String shortUri, ServletRequest request, ServletResponse response) {
        String serverName = request.getServerName();
        String fullShortUrl = serverName + "/" + shortUri;
        LambdaQueryWrapper<ShortLinkGotoDO> shortLinkGOtoQueryWrapper = Wrappers.lambdaQuery(ShortLinkGotoDO.class)
                .eq(ShortLinkGotoDO::getFullShortUrl, fullShortUrl);

        ShortLinkGotoDO shortLinkGotoDO = shortLinkGotoMapper.selectOne(shortLinkGOtoQueryWrapper);
        if (shortLinkGotoDO == null) {
            //  此处需要进行封控
            return;
        }
        LambdaQueryWrapper<ShortLinkDO> queryWrapper = Wrappers.lambdaQuery(ShortLinkDO.class)
                .eq(ShortLinkDO::getGid, shortLinkGotoDO.getGid())
                .eq(ShortLinkDO::getFullShortUrl, fullShortUrl)
                .eq(ShortLinkDO::getEnableStatus, 0)
                .eq(ShortLinkDO::getDelFlag, 0);
        ShortLinkDO shortLinkDO = baseMapper.selectOne(queryWrapper);
        if (shortLinkDO != null) {
            ((HttpServletResponse) response).sendRedirect(shortLinkDO.getOriginUrl());
        }
        if (shortLinkCreateCachePenetrationBloomFilter.contains(fullShortUrl)) {

        }
    }
}
