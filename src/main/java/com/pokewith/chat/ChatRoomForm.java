package com.pokewith.chat;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ChatRoomForm {
    private Long name;
    private String chat;

    @Builder
    public ChatRoomForm(Long name, String chat) {
        this.name = name;
        this.chat = chat;
    }
}
