package com.pokewith.user;

import com.pokewith.user.dto.RqLogInDto;
import com.pokewith.user.dto.RqSignUpDto;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;

public interface UserService {

    ResponseEntity<String> normalSignUp(RqSignUpDto rqSignUpDto);

    ResponseEntity<String> normalLogIn(RqLogInDto rqLogInDto, HttpServletResponse response);
}
