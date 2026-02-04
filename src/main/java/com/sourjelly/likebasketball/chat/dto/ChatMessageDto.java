package com.sourjelly.likebasketball.chat.dto;

import lombok.Data;

@Data
public class ChatMessageDto {
    public enum MessageType{TALK, ENTER, LEAVE}

    private MessageType type; // 메시지 타입
    private Long roomId;      // 방 번호
    private Long senderId;  // 보낸 사람 (ID)
    private String message;   // 내용
}
