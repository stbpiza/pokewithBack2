package com.pokewith.auth;

import com.pokewith.exception.auth.TokenInvalidException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Service
public class UsernameServiceImpl implements UsernameService{


    private final JwtTokenProvider jwtTokenProvider;


    @Override
    public Long getUsername (HttpServletRequest request) {
        try {
            return Long.parseLong(jwtTokenProvider.getUserPk(jwtTokenProvider.resolveToken(request)));
        } catch (Exception e) {
            throw new TokenInvalidException();
        }
    }

    @Override
    public Long getUsernameOrNull (HttpServletRequest request) {
        try {
            return Long.parseLong(jwtTokenProvider.getUserPk(jwtTokenProvider.resolveToken(request)));
        } catch (Exception e) {
            return null;
        }
    }


}
