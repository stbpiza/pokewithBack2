package com.pokewith.chat.controller;

import com.pokewith.auth.UsernameService;
import com.pokewith.chat.ChatRoom;
import com.pokewith.chat.ChatRoomForm;
import com.pokewith.chat.repository.ChatRoomRepository;
import com.pokewith.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Controller
public class ChatController {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatService chatService;
    private final UsernameService usernameService;

    @GetMapping("/allroom")
    public String rooms(Model model) {
        model.addAttribute("rooms", chatRoomRepository.findAllRoom());
        return "rooms";
    }

    @GetMapping("/room/{id}")
    public String room(@PathVariable String id, Model model, HttpServletRequest request) {

        Long userId = usernameService.getUsername(request);

        // 채팅방 없으면 생성
        if(chatRoomRepository.findRoomById(id) == null) {
            // db에 채팅방 id 없으면 메인으로 이동
            if(!chatService.checkChatInRaid(id)) {
                return "index";
            }
            ChatRoomForm form = ChatRoomForm.builder()
                    .name(userId)
                    .chat(id)
                    .build();
            chatRoomRepository.createChatRoom(form);
        }

        // 채팅방 접속
        ChatRoom room = chatRoomRepository.findRoomById(id);
        model.addAttribute("room", room);
        return "room";

    }

    /**
     *  분리한 메소드
     **/


}
