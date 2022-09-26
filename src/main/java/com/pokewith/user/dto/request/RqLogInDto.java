package com.pokewith.user.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Getter @Setter
@ApiModel("일반 로그인")
@NoArgsConstructor
public class RqLogInDto {

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,20}$", message = "비밀번호는 최소 하나의 영어와 숫자, 특수문자 8~20자리여야 합니다.")
    @ApiModelProperty(value = "최소 하나의 영어와 숫자, 특수문자 8~20자리 ^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,20}$", required = true, example = "qazwsx123!")
    private String password;

    @Email
    @ApiModelProperty(value = "email 형식", required = true, example = "1234@poke.com")
    private String email;

}
