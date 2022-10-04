package com.pokewith.chat;

import com.pokewith.auth.UsernameService;
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
    private final UsernameService usernameService;

    @GetMapping("/allroom")
    public String rooms(Model model) {
        model.addAttribute("rooms", chatRoomRepository.findAllRoom());
        return "rooms";
    }

    @GetMapping("/room/{id}")
    public String room(@PathVariable String id, Model model, HttpServletRequest request) {

        Long userId = usernameService.getUsername(request);

        if(chatRoomRepository.findRoomById(id) == null) {
            ChatRoomForm form = new ChatRoomForm();
            form.setChat(id);
            form.setName(userId.toString());
            chatRoomRepository.createChatRoom(form);
        }

        ChatRoom room = chatRoomRepository.findRoomById(id);
        model.addAttribute("room", room);
        return "room";

    }
}
