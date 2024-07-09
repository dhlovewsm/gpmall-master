package com.gpmall.commons.tool.utils;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtils {


    public static String getCookieValue(HttpServletRequest request, String key){
        Cookie[] cookies = request.getCookies();
        if (ArrayUtils.isNotEmpty(cookies)){
            for (Cookie cookie : cookies){
                if (StringUtils.equals(cookie.getName(), key)){
                    return cookie.getValue();
                }
            }
        }
        return null;
    }



    public static Cookie getCookieWithDomain(String key, String value, int maxAge, String domain){
        Cookie cookie = new Cookie(key, value);
        cookie.setDomain(domain);
        enrichCookie(cookie, "/", maxAge);
        return cookie;
    }

    public static Cookie generateCookie(String key, String value, String url, int maxAge){
        Cookie cookie = new Cookie(key, value);
        enrichCookie(cookie, url, maxAge);
        return cookie;
    }

    private static void enrichCookie(Cookie cookie, String url, int maxAge) {
        cookie.setPath(url);
        cookie.setMaxAge(maxAge);
    }


    public static void setCookie(HttpServletResponse response, Cookie cookie) {
        response.addCookie(cookie);
    }


}
