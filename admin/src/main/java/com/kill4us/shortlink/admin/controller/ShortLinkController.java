package com.kill4us.shortlink.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kill4us.shortlink.admin.common.convention.result.Result;
import com.kill4us.shortlink.admin.common.convention.result.Results;
import com.kill4us.shortlink.admin.remote.ShortLinkActualRemoteService;
import com.kill4us.shortlink.admin.remote.ShortLinkRemoteService;
import com.kill4us.shortlink.admin.remote.dto.req.ShortLinkBatchCreateReqDTO;
import com.kill4us.shortlink.admin.remote.dto.req.ShortLinkCreateReqDTO;
import com.kill4us.shortlink.admin.remote.dto.req.ShortLinkPageReqDTO;
import com.kill4us.shortlink.admin.remote.dto.req.ShortLinkUpdateReqDTO;
import com.kill4us.shortlink.admin.remote.dto.resp.ShortLinkBaseInfoRespDTO;
import com.kill4us.shortlink.admin.remote.dto.resp.ShortLinkBatchCreateRespDTO;
import com.kill4us.shortlink.admin.remote.dto.resp.ShortLinkCreateRespDTO;
import com.kill4us.shortlink.admin.remote.dto.resp.ShortLinkPageRespDTO;
import com.kill4us.shortlink.admin.utils.EasyExcelWebUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 后管调用中台短链接接口
 */
@RestController
@RequiredArgsConstructor
public class ShortLinkController {

    private final ShortLinkActualRemoteService shortLinkActualRemoteService;

    ShortLinkRemoteService shortLinkRemoteService = new ShortLinkRemoteService() {
    };
    /**
     * 调用中台进行短链接分页查询
     * @param requestParam
     * @return
     */
    @GetMapping("/api/short-link/admin/v1/page")
    public Result<Page<ShortLinkPageRespDTO>> pageShortLink(ShortLinkPageReqDTO requestParam) {
        return shortLinkActualRemoteService.pageShortLink(requestParam.getGid(), requestParam.getOrderTag(), requestParam.getCurrent(), requestParam.getSize());
    }

    /**
     * 修改短链接
     */
    @PostMapping("/api/short-link/admin/v1/update")
    public Result<Void> updateShortLink(@RequestBody ShortLinkUpdateReqDTO requestParam) {
        shortLinkActualRemoteService.updateShortLink(requestParam);
        return Results.success();
    }

    /**
     * 调用中台创建短链接
     */
    @PostMapping("/api/short-link/admin/v1/create")
    public Result<ShortLinkCreateRespDTO> createShortLink(@RequestBody ShortLinkCreateReqDTO requestParam) {
        return shortLinkActualRemoteService.createShortLink(requestParam);
    }

    /**
     * 批量创建短链接
     */
    @SneakyThrows
    @PostMapping("/api/short-link/admin/v1/create/batch")
    public void batchCreateShortLink(@RequestBody ShortLinkBatchCreateReqDTO requestParam, HttpServletResponse response) {
        Result<ShortLinkBatchCreateRespDTO> shortLinkBatchCreateRespDTOResult = shortLinkActualRemoteService.batchCreateShortLink(requestParam);
        if (shortLinkBatchCreateRespDTOResult.isSuccess()) {
            List<ShortLinkBaseInfoRespDTO> baseLinkInfos = shortLinkBatchCreateRespDTOResult.getData().getBaseLinkInfos();
            EasyExcelWebUtil.write(response, "批量创建短链接-SaaS短链接系统", ShortLinkBaseInfoRespDTO.class, baseLinkInfos);
        }
    }
}
