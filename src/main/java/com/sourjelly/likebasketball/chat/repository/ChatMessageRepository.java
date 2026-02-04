package com.sourjelly.likebasketball.chat.repository;

import com.sourjelly.likebasketball.chat.domain.ChatMessage;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    // 특정 방의 이전 대화 내역 시간순 조회
    List<ChatMessage> findAllByRoomIdOrderByCreatedAtAsc(long roomId);
    @Query(value="SELECT * FROM `chat_message` where `room_id` = :roomId  order by `id` DESC limit 7;", nativeQuery = true)
    List<ChatMessage> findTop7ByRoomIdOrderByCreatedAtAsc(@Param("roomId") long roomId);

//    @Query(value = "select chat from ChatMessage chat where chat.roomId = :roomId AND chat.id > :messageId\n" +
//            "order by chat.id ASC")
//    (@Param("messageId")long messageId, @Param("roomId") long roomId, Pageable pageable);
    @Query(value = "SELECT * FROM `chat_message` where `room_id` = :roomId AND `id` < :messageId order by `id` DESC limit 5", nativeQuery = true)
    List<ChatMessage> findFirst7ByRoomIdAndIdGreaterThanOrderByIdAsc(@Param("roomId") Long roomId, @Param("messageId") Long messageId);
}
