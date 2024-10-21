package com.kill4us.shortlink.admin.controller;

import com.kill4us.shortlink.admin.remote.ShortLinkActualRemoteService;
import com.kill4us.shortlink.admin.remote.ShortLinkRemoteService;
import com.kill4us.shortlink.admin.common.convention.result.Result;
import com.kill4us.shortlink.admin.common.convention.result.Results;
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

    private final ShortLinkActualRemoteService shortLinkActualRemoteService;

    ShortLinkRemoteService shortLinkRemoteService = new ShortLinkRemoteService() {
    };

    /**
     * 根据URL获取网站标题
     */
    @GetMapping("/api/short-link/admin/v1/title")
    public String getTitleByUrl(@RequestParam("url") String url) {
        return shortLinkActualRemoteService.getTitleByUrl(url);
    }
}
