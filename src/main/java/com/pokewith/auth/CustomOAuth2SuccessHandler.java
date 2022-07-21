//package com.pokewith.auth;
//
//import io.lettuce.core.RedisException;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.Optional;
//
//@Slf4j
//@RequiredArgsConstructor
//public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {
//
//    private final RedisService redisService;
//    private final AuthService authService;
//
//    private final TokenValue tokenValue = new TokenValue();
//
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//        // http://localhost:8000/oauth2/authorization/github
//        log.info("야호");
//        log.info(authentication.getDetails().toString());
//        log.info(authentication.getPrincipal().toString());
//        String oauth2Id = "git" + authentication.getName();
//
//        Optional<GitToken> gitToken = redisService.getGitData(oauth2Id);
//
//        if (gitToken.isPresent()) {
//
//            response.getWriter()
//                    .write("{ " +
//                            "\"gitAccessToken\" :  \"" + gitToken.get().getAccessToken() + "\" ," +
//                            "\"oauth2Id\" : \"" + oauth2Id + "\"" +
//                            " }");
//        } else {
//            throw new RedisException("redis 오류");
//        }
//
//    }
//}
