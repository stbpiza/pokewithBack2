package com.pokewith.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Getter @Setter
@Schema(name = "일반 로그인")
@NoArgsConstructor
public class RqLogInDto {

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,20}$",
            message = "비밀번호는 최소 하나의 영어와 숫자, 특수문자 8~20자리여야 합니다.")
    @Schema(description = "최소 하나의 영어와 숫자, 특수문자 8~20자리 ^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,20}$",
            requiredMode = REQUIRED, example = "qazwsx123!")
    private String password;

    @Email
    @Schema(description = "email 형식", requiredMode = REQUIRED, example = "1234@poke.com")
    private String email;

}
