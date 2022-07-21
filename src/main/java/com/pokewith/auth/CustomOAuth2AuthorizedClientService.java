//package com.pokewith.auth;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
//import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
//import org.springframework.security.oauth2.core.OAuth2AccessToken;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.stereotype.Service;
//
//@Slf4j
//@Service
//public class CustomOAuth2AuthorizedClientService implements OAuth2AuthorizedClientService {
//
//    private final RedisService redisService;
//
//    private final TokenValue tokenValue = new TokenValue();
//
//    @Autowired
//    public CustomOAuth2AuthorizedClientService(RedisService redisService) {
//        this.redisService = redisService;
//    }
//
//    @Override
//    public <T extends OAuth2AuthorizedClient> T loadAuthorizedClient(String s, String s1) {
//        return null;
//    }
//
//    @Override
//    public void saveAuthorizedClient(OAuth2AuthorizedClient oAuth2AuthorizedClient, Authentication authentication) {
//        log.info("오오오");
//        OAuth2AccessToken accessToken = oAuth2AuthorizedClient.getAccessToken();
//        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
//        String id = "git" + oAuth2User.getAttributes().get("id");
//        String oauth2Nickname = (String) oAuth2User.getAttributes().get("login");
//
//
//        log.info(oauth2Nickname);
//        log.info(accessToken.getTokenValue());
//        log.info(id);
//
//        GitToken gitToken = GitToken.builder()
//                .accessToken(accessToken.getTokenValue())
//                .oauth2Nickname(oauth2Nickname)
//                .id(id)
//                .timeToLive(tokenValue.getRefreshTokenValidTime())
//                .build();
//        redisService.setGitData(gitToken);
//    }
//
//    @Override
//    public void removeAuthorizedClient(String s, String s1) {
//        throw new UnsupportedOperationException();
//    }
//}
