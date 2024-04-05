package com.kill4us.shortlink.project.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kill4us.shortlink.project.common.convention.result.Result;
import com.kill4us.shortlink.project.common.convention.result.Results;
import com.kill4us.shortlink.project.dto.req.RecycleBinRecoverReqDTO;
import com.kill4us.shortlink.project.dto.req.RecycleBinSaveReqDTO;
import com.kill4us.shortlink.project.dto.req.ShortLinkPageReqDTO;
import com.kill4us.shortlink.project.dto.req.ShortLinkRecycleBinPageReqDTO;
import com.kill4us.shortlink.project.dto.resp.ShortLinkPageRespDTO;
import com.kill4us.shortlink.project.service.RecycleBinService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 短链接回收站控制层
 */
@RestController
@RequiredArgsConstructor
public class RecycleBinController {

    private final RecycleBinService recycleBinService;

    /**
     * 保存回收站短链接
     */
    @PostMapping("/api/short-link/v1/recycle-bin/save")
    public Result<Void> saveRecycleBin(@RequestBody RecycleBinSaveReqDTO requestParam) {
        recycleBinService.saveRecycleBin(requestParam);
        return Results.success();
    }

    /**
     * 分页查询回收站短链接
     */
    @GetMapping("/api/short-link/v1/recycle-bin/page")
    public Result<IPage<ShortLinkPageRespDTO>> pageRecycleBinShortLink(ShortLinkRecycleBinPageReqDTO requestParam) {
        return Results.success(recycleBinService.pageRecycleBinShortLink(requestParam));
    }

    /**
     * 从回收站恢复短链接
     */
    @PostMapping("/api/short-link/v1/recycle-bin/recover")
    public Result<Void> recoverFromCycleBin(@RequestBody RecycleBinRecoverReqDTO requestParam) {
        recycleBinService.recoverFromCycleBin(requestParam);
        return Results.success();
    }
}
