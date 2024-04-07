package com.kill4us.shortlink.project.utils;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;

import javax.swing.text.html.Option;
import java.util.Date;
import java.util.Optional;

import static com.kill4us.shortlink.project.common.constant.ShortLinkConstant.DEFAULT_CACHE_VALID_TIME;

public class LinkUtil {

    /**
     * 获取短链接有效期
     *
     * @param validDate
     * @return
     */
    public static long getLinkCacheValidDate(Date validDate) {
        return Optional.ofNullable(validDate)
                .map(each -> DateUtil.between(new Date(), each, DateUnit.MS))
                .orElse(DEFAULT_CACHE_VALID_TIME);
    }

    /**
     * 获取用户真实ip
     */
    public static String getActualIp(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress;
    }
}
