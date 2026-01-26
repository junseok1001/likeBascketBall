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
@Table(name="`detail_image`")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder(toBuilder = true)
public class GoodsImage {

//    	`id` BIGINT NOT NULL PRIMARY KEY,
//            `goods_id` BIGINT NOT NULL,
//            `good_image` VARCHAR(128) NOT NULL,
//    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
//            `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long goodsId;
    private String goodImage;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
