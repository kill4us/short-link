package com.kill4us.shortlink.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kill4us.shortlink.admin.remote.ShortLinkRemoteService;
import com.kill4us.shortlink.admin.common.convention.result.Result;
import com.kill4us.shortlink.admin.common.convention.result.Results;
import com.kill4us.shortlink.admin.remote.dto.req.RecycleBinRecoverReqDTO;
import com.kill4us.shortlink.admin.remote.dto.req.RecycleBinSaveReqDTO;
import com.kill4us.shortlink.admin.remote.dto.req.ShortLinkPageReqDTO;
import com.kill4us.shortlink.admin.remote.dto.req.ShortLinkRecycleBinPageReqDTO;
import com.kill4us.shortlink.admin.remote.dto.resp.ShortLinkPageRespDTO;
import com.kill4us.shortlink.admin.service.RecycleBinService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 短链接回收站控制层
 */
@RestController
@RequiredArgsConstructor
public class RecycleBinController {

    private final RecycleBinService recycleBinService;

    ShortLinkRemoteService shortLinkRemoteService = new ShortLinkRemoteService() {
    };

    /**
     * 保存回收站
     */
    @PostMapping("/api/short-link/admin/v1/recycle-bin/save")
    public Result<Void> saveRecycleBin(@RequestBody RecycleBinSaveReqDTO requestParam) {
        shortLinkRemoteService.saveRecycleBin(requestParam);
        return Results.success();
    }

    /**
     * 分页查询回收站短链接
     */
    @GetMapping("/api/short-link/admin/v1/recycle-bin/page")
    public Result<IPage<ShortLinkPageRespDTO>> pageRecycleBinShortLink(ShortLinkRecycleBinPageReqDTO requestParam) {
        return recycleBinService.pageRecycleBinShortLink(requestParam);
    }

    /**
     * 从回收站恢复短链接
     */
    @PostMapping("/api/short-link/admin/v1/recycle-bin/recover")
    public Result<Void> recocreFromCycleBin(@RequestBody RecycleBinRecoverReqDTO requestParam) {
        shortLinkRemoteService.recoverFromCycleBin(requestParam);
        return Results.success();
    }
}
