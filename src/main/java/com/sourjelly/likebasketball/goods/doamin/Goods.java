package com.sourjelly.likebasketball.goods.doamin;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name= "`goods`")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Goods {

//    	`id` BIGINT NOT NULL PRIMARY KEY,
//            `user_id` BIGINT NOT NULL,
//            `title` VARCHAR(32) NOT NULL,
//    `price` VARCHAR(32) NOT NULL,
//    `good_info` TEXT,
//            `sales_status` VARCHAR(16) NOT NULL,
//    `good_image` VARCHAR(128) NOT NULL,
//    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
//            `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long userId;
    private String title;
    private int price;
    private String goodInfo;
    private String location;
    @Enumerated(EnumType.STRING)
    private SalesStatus salesStatus;
    private String goodImage;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;


}
