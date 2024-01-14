package com.pokewith.chat.controller;

import com.pokewith.auth.UsernameService;
import com.pokewith.chat.ChatRoom;
import com.pokewith.chat.ChatRoomForm;
import com.pokewith.chat.repository.ChatRoomRepository;
import com.pokewith.chat.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import jakarta.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Controller
public class ChatController {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatService chatService;
    private final UsernameService usernameService;

    @GetMapping("/allroom")
    @Operation(summary = "활성화된 모든 채팅방 리스트 페이지로 이동", description = "활성화된 모든 채팅방 리스트 페이지로 이동(관리자용 페이지)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "활성화된 모든 채팅방 리스트 페이지로 이동")
    })
    public String rooms(Model model) {
        model.addAttribute("rooms", chatRoomRepository.findAllRoom());
        return "rooms";
    }

    @GetMapping("/room/{id}")
    @Operation(summary = "특정 채팅방 페이지로 이동", description = "{id} 채팅방 페이지로 이동(아직 채팅방이 없다면 생성 후 이동)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "{id} 채팅방 페이지로 이동(아직 채팅방이 없다면 생성 후 이동)")
    })
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
