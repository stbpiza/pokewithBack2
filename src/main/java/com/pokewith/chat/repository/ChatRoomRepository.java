package com.pokewith.chat.repository;

import com.pokewith.chat.ChatRoom;
import com.pokewith.chat.ChatRoomForm;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;
import java.util.*;

@EqualsAndHashCode
@Repository
public class ChatRoomRepository {

    private Map<String, ChatRoom> chatRoomMap;

    @PostConstruct
    private void init() {
        chatRoomMap = new LinkedHashMap<>();
    }

    public List<ChatRoom> findAllRoom() {
        List<ChatRoom> chatRooms = new ArrayList<>(chatRoomMap.values());
        Collections.reverse(chatRooms);
        return chatRooms;
    }

    public ChatRoom findRoomById(String id) {
        return chatRoomMap.get(id);
    }

    public ChatRoom createChatRoom(ChatRoomForm form) {
        ChatRoom chatRoom = ChatRoom.create(form);
        chatRoomMap.put(chatRoom.getRoomId(), chatRoom);
        return chatRoom;
    }

    public void deleteChatRoom(String id) {
        chatRoomMap.remove(id);
    }
}
