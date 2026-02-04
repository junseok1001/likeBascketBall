package com.sourjelly.likebasketball.main.service;

import com.sourjelly.likebasketball.club.dto.SelectClubDto;
import com.sourjelly.likebasketball.club.dto.ShowClubDto;
import com.sourjelly.likebasketball.club.service.ClubService;
import com.sourjelly.likebasketball.common.global.CustomException;
import com.sourjelly.likebasketball.common.global.ErrorCode;
import com.sourjelly.likebasketball.goods.dto.ShowGoods;
import com.sourjelly.likebasketball.goods.service.GoodsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MainService {

    private final ClubService clubService;
    private final GoodsService goodsService;

    public List<ShowClubDto> getClubs(){

        if(clubService.getClubInfo() != null){
            return clubService.getClubInfo();
        }else{
            return null;
        }
    }

    public List<ShowGoods> findAllGoods(){

        if(goodsService.findGoodsByCreatedDesc() == null){
            throw new CustomException(ErrorCode.GOODS_NOT_FOUND);
        }

        return goodsService.findGoodsByCreatedDesc();
    }



}
