package edu.upc.mishuserverapi.utils;

import javax.servlet.http.HttpServletRequest;

public class HttpRequestUtil {
    /**
     * 在有nginx代理的情况下获取用户ip，要求在nginx下进行相关设置
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        if (request.getHeader("x-forwarded-for") == null) {  
            return request.getRemoteAddr();  
        }  
        return request.getHeader("x-forwarded-for");      
    }
}