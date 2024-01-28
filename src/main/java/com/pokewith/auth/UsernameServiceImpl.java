package com.pokewith.auth;

import com.pokewith.exception.UnauthorizedException;
import com.pokewith.exception.auth.TokenInvalidException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RequiredArgsConstructor
@Service
public class UsernameServiceImpl implements UsernameService{


    private final JwtTokenProvider jwtTokenProvider;


    @Override
    public Long getUsername (HttpServletRequest request) {
        try {
            HttpSession session = request.getSession();
            Long userId = (Long) session.getAttribute("userId");
            if (userId == null) {
                throw new UnauthorizedException();
            }
            return userId;
        } catch (Exception e) {
            throw new UnauthorizedException();
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
