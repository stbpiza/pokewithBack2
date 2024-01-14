package com.pokewith.user.service;

import com.pokewith.user.dto.request.RqEmailCheckDto;
import com.pokewith.user.dto.request.RqLogInDto;
import com.pokewith.user.dto.request.RqSignUpDto;
import org.springframework.http.ResponseEntity;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface UserService {

    ResponseEntity<String> normalSignUp(RqSignUpDto rqSignUpDto);

    ResponseEntity<String> normalLogIn(RqLogInDto rqLogInDto, HttpServletResponse response, HttpServletRequest request);

    ResponseEntity<String> emailCheck(RqEmailCheckDto rqEmailCheckDto);

}
