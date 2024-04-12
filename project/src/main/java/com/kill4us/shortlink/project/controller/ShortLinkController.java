package com.kill4us.shortlink.project.controller;

import cn.hutool.http.server.HttpServerRequest;
import cn.hutool.http.server.HttpServerResponse;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kill4us.shortlink.project.common.convention.result.Result;
import com.kill4us.shortlink.project.common.convention.result.Results;
import com.kill4us.shortlink.project.dto.req.ShortLinkBatchCreateReqDTO;
import com.kill4us.shortlink.project.dto.req.ShortLinkCreateReqDTO;
import com.kill4us.shortlink.project.dto.req.ShortLinkPageReqDTO;
import com.kill4us.shortlink.project.dto.req.ShortLinkUpdateReqDTO;
import com.kill4us.shortlink.project.dto.resp.ShortLinkBatchCreateRespDTO;
import com.kill4us.shortlink.project.dto.resp.ShortLinkCountQueryRespDTO;
import com.kill4us.shortlink.project.dto.resp.ShortLinkCreateRespDTO;
import com.kill4us.shortlink.project.dto.resp.ShortLinkPageRespDTO;
import com.kill4us.shortlink.project.handler.CustomBlockHandler;
import com.kill4us.shortlink.project.service.ShortLinkService;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 短链接控制层
 */
@RestController
@RequiredArgsConstructor
public class ShortLinkController {

    private final ShortLinkService shortLinkService;

    @GetMapping("/{short-uri}")
    private void restoreUrl(@PathVariable("short-uri") String short_uri, ServletRequest request, ServletResponse response) {
        shortLinkService.restoreUrl(short_uri, request, response);
    }

    /**
     * 创建短链接
     */
    @PostMapping("/api/short-link/v1/create")
    @SentinelResource(
            value = "create_short-link",
            blockHandler = "createShortLinkBlockHandlerMethod",
            blockHandlerClass = CustomBlockHandler.class
    )
    public Result<ShortLinkCreateRespDTO> createShortLink(@RequestBody ShortLinkCreateReqDTO requestParam) {
        return Results.success(shortLinkService.createShortLink(requestParam));
    }

    /**
     * 修改短链接
     */
    @PutMapping("/api/short-link/v1/update")
    public Result<Void> updateShortLink(@RequestBody ShortLinkUpdateReqDTO requestParam) {
        shortLinkService.updateShortLink(requestParam);
        return Results.success();
    }

    /**
     * 分页查询短链接
     */
    @GetMapping("/api/short-link/v1/page")
    public Result<IPage<ShortLinkPageRespDTO>> pageShortLink(ShortLinkPageReqDTO requestParam) {
        return Results.success(shortLinkService.pageShortLink(requestParam));
    }

    /**
     * 短链接分组内数量查询
     */
    @GetMapping("/api/short-link/v1/count")
    public Result<List<ShortLinkCountQueryRespDTO>> listGroupShortLinkCount(@RequestParam List<String> requestParam) {
        return Results.success(shortLinkService.listGroupShortLinkCount(requestParam));
    }

    /**
     * 批量创建短链接
     */
    @PostMapping("/api/short-link/v1/create/batch")
    public Result<ShortLinkBatchCreateRespDTO> batchCreateShortLink(@RequestBody ShortLinkBatchCreateReqDTO requestParam) {
        return Results.success(shortLinkService.batchCreateShortLink(requestParam));
    }
}
