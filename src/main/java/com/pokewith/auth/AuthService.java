package com.pokewith.auth;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public interface AuthService {

    // CookieUtil 에서 구현
    Cookie createCookie(String cookieName, String value);
    Cookie getCookie(HttpServletRequest request, String cookieName);


    Cookie deleteCookie(String cookieName);
}
