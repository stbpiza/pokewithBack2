package com.pokewith.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(name = "유효성 검사 결과")
public class ResponseDto {

    @Schema(description = "항목명(dto 이름, 배열 이름)", example = "EducationDto")
    private String objectName;
    @Schema(description = "변수명", example = "educationName")
    private String field;
//    @Schema(description = "오류 메세지", example = "null 일 수 없습니다.")
//    private String message;

    @Builder
    public ResponseDto(String objectName, String field) {
        this.objectName = objectName;
        this.field = field;
//        this.message = message;
    }
}
