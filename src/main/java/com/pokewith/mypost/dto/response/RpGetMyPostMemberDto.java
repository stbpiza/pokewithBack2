package com.pokewith.mypost.dto.response;

import com.pokewith.mypost.dto.MyPostMemberDto;
import com.pokewith.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
@NoArgsConstructor
public class RpGetMyPostMemberDto {

    private MyPostMemberDto writer;

    private List<MyPostMemberDto> commenter;

    public RpGetMyPostMemberDto(User writer, List<User> commenter) {
        this.writer = new MyPostMemberDto(writer);
        this.commenter = commenter.stream()
                .map(MyPostMemberDto::new).collect(Collectors.toList());
    }
}
