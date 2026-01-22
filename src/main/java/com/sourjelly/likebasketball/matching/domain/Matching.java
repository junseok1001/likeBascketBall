package com.sourjelly.likebasketball.matching.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


@Entity
@Table(name = "`matching`")
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Matching {

//    	`id` BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
//    `challenge_club` BIGINT NOT NULL,
//            `away_club` BIGINT NOT NULL,
//            `game_date` DATE NOT NULL,
//            `game_time` DATETIME NOT NULL,
//            `location` VARCHAR(32) NOT NULL,
//    `content` TEXT,
//            `phone_number` VARCHAR(32) NOT NULL,
//    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
//            `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long challengeClub;
    private long awayClub;
    private LocalDate gameDate; // 이거가 중요
    private LocalTime gameTime;// 이거 중요
    @Enumerated(EnumType.STRING)
    private MatchStatus matchStatus;
    private String locationName;
    private String location;
    private String content;
    private String phoneNumber;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
