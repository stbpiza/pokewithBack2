package com.pokewith.chat.service;

import com.pokewith.raid.Raid;
import com.pokewith.raid.repository.RaidQueryRepository;
import com.pokewith.raid.repository.RaidRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService{

    private final RaidRepository raidRepository;
    private final RaidQueryRepository raidQueryRepository;

    @Override
    public boolean checkChatInRaid(String chat) {
        Optional<Raid> raid = raidQueryRepository.getDoingRaidByChat(chat);
        return raid.isPresent();
    }
}
