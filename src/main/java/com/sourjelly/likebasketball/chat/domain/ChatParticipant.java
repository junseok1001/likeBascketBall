package com.sourjelly.likebasketball.chat.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
//@Table(name = "`chat_participant`")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder(toBuilder = true)
public class ChatParticipant {

//    	`id` BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
//    `room_id` BIGINT NOT NULL,
//            `user_id` BIGINT NOT NULL,
//            `last_read_id` BIGINT DEFAULT 0,
//            `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
//            `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    private long roomId;
    private long userId;
    private long lastReadId;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
