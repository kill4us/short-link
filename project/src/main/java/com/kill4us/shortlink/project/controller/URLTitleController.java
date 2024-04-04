package com.kill4us.shortlink.project.controller;

import com.kill4us.shortlink.project.common.convention.result.Result;
import com.kill4us.shortlink.project.common.convention.result.Results;
import com.kill4us.shortlink.project.service.URLTitleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * URL标题控制层
 */
@RestController
@RequiredArgsConstructor
public class URLTitleController {

    private final URLTitleService urlTitleService;

    /**
     * 根据URL获取网站标题
     */
    @GetMapping("/api/short-link/title")
    public Result<String> getTitleByUrl(@RequestParam("url") String url) {
        return Results.success(urlTitleService.getTitleByUrl(url));
    }
}
