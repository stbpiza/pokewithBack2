package com.pokewith.auth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {

    // CookieUtil 에서 구현
    Cookie createCookie(String cookieName, String value);
    Cookie getCookie(HttpServletRequest request, String cookieName);


    Cookie deleteCookie(String cookieName);
}
