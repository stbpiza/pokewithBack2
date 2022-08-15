package com.pokewith.user;

import com.pokewith.auth.AuthService;
import com.pokewith.auth.JwtTokenProvider;
import com.pokewith.user.dto.RqSignUpDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthService authService;
    private final EntityManager em;

    @Transactional
    @Override
    public ResponseEntity<String> normalSignUp(RqSignUpDto rqSignUpDto) {

        User user = normalSignUpSetUser(rqSignUpDto);

        em.persist(user);

        em.flush();

        return new ResponseEntity<>("", HttpStatus.OK);
    }


    /**
     *  분리한 메소드
     **/

    private User normalSignUpSetUser(RqSignUpDto rqSignUpDto) {
        return User.NormalSignUpBuilder()
                .email(rqSignUpDto.getEmail())
                .password(passwordEncoder.encode(rqSignUpDto.getPassword()))
                .nickname1(rqSignUpDto.getNickname1())
                .friendCode1(rqSignUpDto.getFriendCode1())
                .build();
    }

    private String createAccessToken(User user) {
        List<String> roles = new ArrayList<>();
        roles.add(user.getUserType().toString());

        return jwtTokenProvider.createToken(user.getUserIdToString(), roles);
    }
}