package com.pokewith.user;

import com.pokewith.user.dto.RqSignUpDto;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<String> normalSignUp(RqSignUpDto rqSignUpDto);

}
