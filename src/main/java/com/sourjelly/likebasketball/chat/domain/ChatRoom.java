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
//@Table(name = "`chat_room")
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoom {
    public enum RoomType {CLUB, GOODS}
//    `id` BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
//    `room_type` VARCHAR(8) NOT NULL,
//    `target_id` BIGINT NOT NULL,
//            `room_name` VARCHAR(128),
//    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
//            `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Enumerated(EnumType.STRING)
    private RoomType roomType;
    private long targetId;
    private long senderId;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
