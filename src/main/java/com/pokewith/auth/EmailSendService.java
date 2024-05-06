package com.pokewith.auth;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSendService implements EmailService {

    private final JwtTokenProvider jwtTokenProvider;
    private final RedisService redisService;
//    private final JavaMailSender javaMailSender;

    @Value("${text.myurl}")
    private String myurl;

    @Override
    public void signUpEmail(String userId, String email) {

        String random = RandomStringUtils.random(30);

        EmailToken emailToken = EmailToken.builder()
                .id(userId)
                .random(random)
                .timeToLive(TokenValue.emailTokenValidTime)
                .build();

        redisService.setEmailData(emailToken);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setFrom("no-reply@abc.com");
        mailMessage.setSubject("회원가입 이메일 인증입니다.");
        mailMessage.setText(myurl + "/auth/email/" + userId + "/" + random);
        sendEmail(mailMessage);

    }

    @Override
    public void lostPasswordEmail(String userId, String email) {

        String random = RandomStringUtils.random(30);

        EmailToken emailToken = EmailToken.builder()
                .id(userId)
                .random(random)
                .timeToLive(TokenValue.emailTokenValidTime)
                .build();

        redisService.setEmailData(emailToken);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setFrom("no-reply@abc.com");
        mailMessage.setSubject("비밀번호 찾기 이메일입니다.");
        mailMessage.setText(myurl + "/auth/password/" + userId + "/" + random);
        sendEmail(mailMessage);


    }



    @Async
    public void sendEmail(SimpleMailMessage email) {
//        javaMailSender.send(email);
    }


}
