package com.pokewith.auth;

import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Service
public class CookieUtil implements AuthService{

    private final TokenValue tokenValue = new TokenValue();

    @Override
    public Cookie createCookie(String cookieName, String value) {
        Cookie token = new Cookie(cookieName, value);
        token.setHttpOnly(true);
        token.setMaxAge(tokenValue.getRefreshTokenValidTime().intValue()/1000);
        token.setSecure(false);
        token.setPath("/");
        return token;
    }

    @Override
    public Cookie getCookie(HttpServletRequest request, String cookieName) {
        final Cookie[] cookies = request.getCookies();
        if(cookies==null) return null;
        for(Cookie cookie : cookies) {
            if(cookie.getName().equals(cookieName))
                return cookie;
        }
        return null;
    }

    @Override
    public Cookie deleteCookie(String cookieName) {

        Cookie token = new Cookie(cookieName, "");
        token.setHttpOnly(true);
        token.setMaxAge(1);
        token.setSecure(false);
        token.setPath("/");

        return token;
    }


}
