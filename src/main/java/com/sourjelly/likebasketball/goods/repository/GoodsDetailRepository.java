package com.sourjelly.likebasketball.goods.repository;

import com.sourjelly.likebasketball.goods.doamin.GoodsImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoodsDetailRepository extends JpaRepository<GoodsImage, Long> {
    List<GoodsImage> findByGoodsId(long goodsId);
}
