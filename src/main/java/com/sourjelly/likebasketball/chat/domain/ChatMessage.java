package com.sourjelly.likebasketball.chat.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
//@Table(name="`chat_message")
@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {

//    	`id` BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
//	`room_id` BIGINT NOT NULL,
//            `sender_id` BIGINT NOT NULL,
//            `message` TEXT NOT NULL,
//            `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    private long roomId;
    private long senderId;
    private String message;
    @CreationTimestamp
    private LocalDateTime createdAt;
}
