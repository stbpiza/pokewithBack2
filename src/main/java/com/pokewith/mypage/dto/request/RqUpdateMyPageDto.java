package com.pokewith.mypage.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter @Setter
@NoArgsConstructor
@ApiModel("마이페이지 정보 수정")
public class RqUpdateMyPageDto {

    @NotNull
    @Pattern(regexp = "^[A-Za-z\\d]{2,20}$", message = "닉네임은 영어, 숫자 2~20자리여야 합니다.")
    @ApiModelProperty(value = "영어, 숫자 2~20자리 ^[A-Za-z\\d]{2,20}$", required = true, example = "pokemon1")
    private String nickname1;

    @NotNull
    @Pattern(regexp = "^[\\d]{4}[-][\\d]{4}[-][\\d]{4}[-][\\d]{4}$", message = "친구코드는 - 포함 숫자 16자리 1234-1234-1234-1234")
    @ApiModelProperty(value = "- 포함 숫자 16자리 1234-1234-1234-1234 ^[\\d]{4}[-][\\d]{4}[-][\\d]{4}[-][\\d]{4}$", required = true, example = "pokemon1")
    private String friendCode1;

    @Pattern(regexp = "^[A-Za-z\\d]{2,20}$", message = "닉네임은 영어, 숫자 2~20자리여야 합니다.")
    @ApiModelProperty(value = "영어, 숫자 2~20자리 ^[A-Za-z\\d]{2,20}$", example = "pokemon1")
    private String nickname2;

    @Pattern(regexp = "^[\\d]{4}[-][\\d]{4}[-][\\d]{4}[-][\\d]{4}$", message = "친구코드는 - 포함 숫자 16자리 1234-1234-1234-1234")
    @ApiModelProperty(value = "- 포함 숫자 16자리 1234-1234-1234-1234 ^[\\d]{4}[-][\\d]{4}[-][\\d]{4}[-][\\d]{4}$", example = "pokemon1")
    private String friendCode2;

    @Pattern(regexp = "^[A-Za-z\\d]{2,20}$", message = "닉네임은 영어, 숫자 2~20자리여야 합니다.")
    @ApiModelProperty(value = "영어, 숫자 2~20자리 ^[A-Za-z\\d]{2,20}$", example = "pokemon1")
    private String nickname3;

    @Pattern(regexp = "^[\\d]{4}[-][\\d]{4}[-][\\d]{4}[-][\\d]{4}$", message = "친구코드는 - 포함 숫자 16자리 1234-1234-1234-1234")
    @ApiModelProperty(value = "- 포함 숫자 16자리 1234-1234-1234-1234 ^[\\d]{4}[-][\\d]{4}[-][\\d]{4}[-][\\d]{4}$", example = "pokemon1")
    private String friendCode3;

    @Pattern(regexp = "^[A-Za-z\\d]{2,20}$", message = "닉네임은 영어, 숫자 2~20자리여야 합니다.")
    @ApiModelProperty(value = "영어, 숫자 2~20자리 ^[A-Za-z\\d]{2,20}$", example = "pokemon1")
    private String nickname4;

    @Pattern(regexp = "^[\\d]{4}[-][\\d]{4}[-][\\d]{4}[-][\\d]{4}$", message = "친구코드는 - 포함 숫자 16자리 1234-1234-1234-1234")
    @ApiModelProperty(value = "- 포함 숫자 16자리 1234-1234-1234-1234 ^[\\d]{4}[-][\\d]{4}[-][\\d]{4}[-][\\d]{4}$", example = "pokemon1")
    private String friendCode4;

    @Pattern(regexp = "^[A-Za-z\\d]{2,20}$", message = "닉네임은 영어, 숫자 2~20자리여야 합니다.")
    @ApiModelProperty(value = "영어, 숫자 2~20자리 ^[A-Za-z\\d]{2,20}$", example = "pokemon1")
    private String nickname5;

    @Pattern(regexp = "^[\\d]{4}[-][\\d]{4}[-][\\d]{4}[-][\\d]{4}$", message = "친구코드는 - 포함 숫자 16자리 1234-1234-1234-1234")
    @ApiModelProperty(value = "- 포함 숫자 16자리 1234-1234-1234-1234 ^[\\d]{4}[-][\\d]{4}[-][\\d]{4}[-][\\d]{4}$", example = "pokemon1")
    private String friendCode5;

}
