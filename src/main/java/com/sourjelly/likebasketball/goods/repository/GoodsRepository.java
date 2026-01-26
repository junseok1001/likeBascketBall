package com.sourjelly.likebasketball.goods.repository;

import com.sourjelly.likebasketball.goods.doamin.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoodsRepository extends JpaRepository<Goods, Long> {

    public List<Goods> findAllByOrderByCreatedAtDesc();
}
