package com.kill4us.shortlink.project.service;

/**
 * URL获取标题接口层
 */
public interface URLTitleService {

    /**
     * 根据url获取网站标题
     * @param url
     * @return 标题
     */
    String getTitleByUrl(String url);
}
