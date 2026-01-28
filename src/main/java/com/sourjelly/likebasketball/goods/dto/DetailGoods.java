package com.sourjelly.likebasketball.goods.dto;

import com.sourjelly.likebasketball.goods.doamin.GoodsImage;
import com.sourjelly.likebasketball.goods.doamin.SalesStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder(toBuilder = true)
public class DetailGoods {

    private long goodsId;
    private long userId;
    private String title;
    private int price;
    private String location;
    private String goodsInfo;
    private String mainImage;
    private SalesStatus salesStatus;

    private List<GoodsImage> detailImage;
}
