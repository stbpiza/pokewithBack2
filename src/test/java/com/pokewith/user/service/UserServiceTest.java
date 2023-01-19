package com.pokewith.user.service;

import com.pokewith.exception.NotFoundException;
import com.pokewith.user.User;
import com.pokewith.user.UserState;
import com.pokewith.user.UserType;
import com.pokewith.user.dto.request.RqSignUpDto;
import com.pokewith.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    private final String email = "abc@abc.com";
    private final String password = "1111";
    private final String nickname = "tester";
    private final String friendCode = "0000-0000-0000-0000";

    @BeforeAll
    void before() {

        User user = User.NormalSignUpBuilder()
                .email(email)
                .password(password)
                .nickname1(nickname)
                .friendCode1(friendCode)
                .build();

        userRepository.save(user);

    }

    @Test
    void 일반가입테스트() {

        String signUpEmail = "abcd@abc.com";
        String signUpPassword = "1111";
        String signUpNickname = "signUpTest";
        String signUpFriendCode = "1111-0000-0000-0000";

        RqSignUpDto dto = new RqSignUpDto();

        dto.setEmail(signUpEmail);
        dto.setPassword(signUpPassword);
        dto.setNickname1(signUpNickname);
        dto.setFriendCode1(signUpFriendCode);


        userService.normalSignUp(dto);


        User user = userRepository.findByEmail(signUpEmail)
                .orElseThrow(NotFoundException::new);

        assertThat(user.getUserType(), is(equalTo(UserType.ROLE_NOTUSER)));
        assertThat(user.getUserState(), is(equalTo(UserState.FREE)));
        assertThat(user.getEmail(), is(equalTo(signUpEmail)));
        boolean checkPassword = passwordEncoder.matches(signUpPassword, user.getPassword());
        assertThat(checkPassword, is(true));
        assertThat(user.getNickname1(), is(equalTo(signUpNickname)));
        assertThat(user.getFriendCode1(), is(equalTo(signUpFriendCode)));
    }


}
