package com.sourjelly.likebasketball.chat.repository;

import com.sourjelly.likebasketball.chat.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    // 상품 ID와 타입으로 기존 방이 있는지 확인
    Optional<ChatRoom> findByRoomTypeAndTargetId(ChatRoom.RoomType roomType, long targetId);
}
