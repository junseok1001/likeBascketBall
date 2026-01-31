package com.sourjelly.likebasketball.chat.repository;

import com.sourjelly.likebasketball.chat.domain.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    // 특정 방의 이전 대화 내역 시간순 조회
    List<ChatMessage> findAllByRoomIdOrderByCreatedAtAsc(long roomId);
}
