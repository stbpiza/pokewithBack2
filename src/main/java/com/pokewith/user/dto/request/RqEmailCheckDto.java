package com.pokewith.user.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;

@Getter @Setter
@ApiModel("이메일 중복 확인")
@NoArgsConstructor
public class RqEmailCheckDto {

    @Email
    @ApiModelProperty(value = "email 형식", required = true, example = "1234@poke.com")
    private String email;

}
