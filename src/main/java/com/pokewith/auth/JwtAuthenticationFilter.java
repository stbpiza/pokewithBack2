package com.pokewith.auth;

import com.pokewith.exception.ErrorCode;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    private final TokenValue tokenValue = new TokenValue();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 쿠키에서 JWT를 받아옵니다.
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);

        // 유효한 토큰인지 확인합니다.
        try {
            if (token != null && jwtTokenProvider.validateToken(token)) {
                // 토큰이 유효하면 토큰으로부터 유저 정보를 받아옵니다.
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                // SecurityContext 에 Authentication 객체를 저장합니다.
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else { throw new SignatureException(""); }
        } catch (SignatureException e) {
            request.setAttribute(tokenValue.getStatusValue(), ErrorCode.INVALID_TOKEN.getStatus().value());
        } catch (ExpiredJwtException e) {
            request.setAttribute(tokenValue.getStatusValue(), ErrorCode.TIME_OUT_TOKEN.getStatus().value());
        } catch (Exception e) {
            request.setAttribute(tokenValue.getStatusValue(), ErrorCode.ETC_TOKEN.getStatus().value());
        }


        chain.doFilter(request, response);
    }
}
