package com.pokewith.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.Email;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Getter @Setter
@Schema(name = "이메일 중복 확인")
@NoArgsConstructor
public class RqEmailCheckDto {

    @Email
    @Schema(description = "email 형식", requiredMode = REQUIRED, example = "1234@poke.com")
    private String email;

}
