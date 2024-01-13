package com.pokewith.user.service;

import com.pokewith.auth.*;
import com.pokewith.exception.auth.LoginFailedException;
import com.pokewith.user.User;
import com.pokewith.user.repository.UserRepository;
import com.pokewith.user.dto.request.RqEmailCheckDto;
import com.pokewith.user.dto.request.RqLogInDto;
import com.pokewith.user.dto.request.RqSignUpDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthService authService;
    private final RedisService redisService;
    private final EntityManager em;

    private final TokenValue tokenValue = new TokenValue();
    private final String USERID = "userId";
    private final String NICKNAME1 = "nickname1";

    @Transactional
    @Override
    public ResponseEntity<String> normalSignUp(RqSignUpDto rqSignUpDto) {

        User user = normalSignUpSetUser(rqSignUpDto);

        em.persist(user);

        em.flush();

        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> normalLogIn(RqLogInDto rqLogInDto, HttpServletResponse response, HttpServletRequest request) {

        User member = checkLoginUser(rqLogInDto);

        String token = createAccessToken(member, response);

        redisService.setNormalData(NormalToken.builder()
                        .username(member.getUserIdToString())
                        .refreshToken(token)
                        .userType(member.getUserType().toString())
                        .timeToLive(tokenValue.getTokenValidTime())
                .build());

        createSession(member, request);

        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> emailCheck(RqEmailCheckDto rqEmailCheckDto) {

        Optional<User> member = findByEmailCheck(rqEmailCheckDto);

        if(member.isEmpty()) {
            return new ResponseEntity<>("사용 가능한 이메일입니다.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("이미 사용중인 이메일입니다.", HttpStatus.CONFLICT);
        }
    }


    /**
     *  분리한 메소드
     **/

    protected User normalSignUpSetUser(RqSignUpDto rqSignUpDto) {
        return User.NormalSignUpBuilder()
                .email(rqSignUpDto.getEmail())
                .password(passwordEncoder.encode(rqSignUpDto.getPassword()))
                .nickname1(rqSignUpDto.getNickname1())
                .friendCode1(rqSignUpDto.getFriendCode1())
                .build();
    }

    protected User checkLoginUser(RqLogInDto rqLogInDto) {
        User user = userRepository.findByEmail(rqLogInDto.getEmail())
                .orElseThrow(LoginFailedException::new);
        if(!passwordEncoder.matches(rqLogInDto.getPassword(), user.getPassword())) {
            throw new LoginFailedException();
        }
        return user;
    }

    protected String createAccessToken(User user, HttpServletResponse response) {
        List<String> roles = new ArrayList<>();
        roles.add(user.getUserType().toString());

        String token = jwtTokenProvider.createToken(user.getUserIdToString(), roles);
        response.addCookie(authService.createCookie(tokenValue.getAccessToken(), token));
        return token;
    }

    protected void createSession(User user, HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute(USERID, user.getUserId());
        session.setAttribute(NICKNAME1, user.getNickname1());
        session.setMaxInactiveInterval(24*60*60); //24시간
    }

    protected Optional<User> findByEmailCheck(RqEmailCheckDto rqEmailCheckDto) {
        return userRepository.findByEmail(rqEmailCheckDto.getEmail());
    }

}
