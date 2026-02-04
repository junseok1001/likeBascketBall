package com.sourjelly.likebasketball.chat.repository;

import com.sourjelly.likebasketball.chat.domain.ChatParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatParticipantRepository extends JpaRepository<ChatParticipant, Long> {

    // 유저가 참여 중인 모든 방의 ID 목록 조회
    List<ChatParticipant> findAllByUserId(long userId);
    // roomId로 참여자 목록 가져오기
    List<ChatParticipant> findAllByRoomId(long roomId);
}
