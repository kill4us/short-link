package com.kill4us.shortlink.project.service;

import cn.hutool.http.server.HttpServerRequest;
import cn.hutool.http.server.HttpServerResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kill4us.shortlink.project.dao.entity.ShortLinkDO;
import com.kill4us.shortlink.project.dto.req.ShortLinkCreateReqDTO;
import com.kill4us.shortlink.project.dto.req.ShortLinkPageReqDTO;
import com.kill4us.shortlink.project.dto.req.ShortLinkUpdateReqDTO;
import com.kill4us.shortlink.project.dto.resp.ShortLinkCountQueryRespDTO;
import com.kill4us.shortlink.project.dto.resp.ShortLinkCreateRespDTO;
import com.kill4us.shortlink.project.dto.resp.ShortLinkPageRespDTO;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

import java.util.List;

/**
 * 短链接接口层
 */
public interface ShortLinkService extends IService<ShortLinkDO> {

    /**
     * 创建短链接
     * @param requestParam 创建短链接请求参数
     * @return 短链接创建信息
     */
    ShortLinkCreateRespDTO createShortLink(ShortLinkCreateReqDTO requestParam);

    /**
     * 分页查询短链接
     * @param requestParam 分页查询短链接请求参数
     * @return 分页结果
     */
    IPage<ShortLinkPageRespDTO> pageShortLink(ShortLinkPageReqDTO requestParam);

    /**
     * 查询分组内短链接数量
     * @param requestParam
     * @return  list
     */
    List<ShortLinkCountQueryRespDTO> listGroupShortLinkCount(List<String> requestParam);

    /**
     * 更新短链接信息
     * @param requestParam
     */
    void updateShortLink(ShortLinkUpdateReqDTO requestParam);

    /**
     * 短链接跳转接口
     * @param shortUri 短链接后缀
     * @param request   请求
     * @param response  响应
     */
    void restoreUrl(String shortUri, ServletRequest request, ServletResponse response);
}
