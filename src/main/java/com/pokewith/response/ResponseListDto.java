package com.pokewith.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class ResponseListDto {

    private List<ResponseDto> responseDtoList = new ArrayList<>();

    @Builder
    public ResponseListDto(List<ResponseDto> responseDtoList) {
        this.responseDtoList = responseDtoList;
    }
}
