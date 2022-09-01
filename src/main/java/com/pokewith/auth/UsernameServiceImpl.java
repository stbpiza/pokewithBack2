package com.pokewith.auth;

import com.pokewith.exception.auth.TokenInvalidException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Service
public class UsernameServiceImpl implements UsernameService{


    private final JwtTokenProvider jwtTokenProvider;


    @Override
    public Long getUsername (HttpServletRequest request) {
        try {
            HttpSession session = request.getSession();
            return (Long) session.getAttribute("userId");
        } catch (Exception e) {
            throw new TokenInvalidException();
        }
    }

    @Override
    public Long getUsernameOrNull (HttpServletRequest request) {
        try {
            HttpSession session = request.getSession();
            return (Long) session.getAttribute("userId");
        } catch (Exception e) {
            return null;
        }
    }


}
