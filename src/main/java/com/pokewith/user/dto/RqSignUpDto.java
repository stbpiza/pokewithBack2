package com.pokewith.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter @Setter
@ApiModel("일반 회원가입")
@NoArgsConstructor
public class RqSignUpDto {

    @NotNull
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,20}$", message = "비밀번호는 최소 하나의 영어와 숫자, 특수문자 8~20자리여야 합니다.")
    @ApiModelProperty(value = "최소 하나의 영어와 숫자, 특수문자 8~20자리 ^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,20}$", required = true, example = "qazwsx123!")
    private String password;

    @NotNull
    @Email
    @ApiModelProperty(value = "email 형식", required = true, example = "1234@poke.com")
    private String email;

    @NotNull
    @Pattern(regexp = "^[A-Za-z\\d]{2,20}$", message = "닉네임은 영어, 숫자 2~20자리여야 합니다.")
    @ApiModelProperty(value = "영어, 숫자 2~20자리 ^[A-Za-z\\d]{2,20}$", required = true, example = "pokemon1")
    private String nickname1;

    @NotNull
    @Pattern(regexp = "^[\\d]{4}[-][\\d]{4}[-][\\d]{4}[-][\\d]{4}$", message = "친구코드는 - 포함 숫자 16자리 1234-1234-1234-1234")
    @ApiModelProperty(value = "- 포함 숫자 16자리 1234-1234-1234-1234 ^[\\d]{4}[-][\\d]{4}[-][\\d]{4}[-][\\d]{4}$", required = true, example = "pokemon1")
    private String friendCode1;
}
