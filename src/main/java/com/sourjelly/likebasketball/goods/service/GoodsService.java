package com.sourjelly.likebasketball.goods.service;

import com.sourjelly.likebasketball.common.fileManger.FileManger;
import com.sourjelly.likebasketball.common.global.CustomException;
import com.sourjelly.likebasketball.common.global.ErrorCode;
import com.sourjelly.likebasketball.goods.doamin.Goods;
import com.sourjelly.likebasketball.goods.doamin.GoodsImage;
import com.sourjelly.likebasketball.goods.doamin.SalesStatus;
import com.sourjelly.likebasketball.goods.dto.CreateGoodsDto;
import com.sourjelly.likebasketball.goods.dto.ShowGoods;
import com.sourjelly.likebasketball.goods.repository.GoodsDetailRepository;
import com.sourjelly.likebasketball.goods.repository.GoodsRepository;
import com.sourjelly.likebasketball.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GoodsService {

    private final GoodsRepository goodsRepository;
    private final GoodsDetailRepository goodsDetailRepository;
    // 상품 등록
    public void createGoods(User user , CreateGoodsDto createGoodsDto){

        String mainImagePath = FileManger.saveGoodsMain(user.getId() ,createGoodsDto.getMainImage());

        Goods goods = Goods.builder()
                           .userId(user.getId())
                           .title(createGoodsDto.getTitle())
                           .price(createGoodsDto.getPrice())
                            .location(createGoodsDto.getLocation())
                           .goodInfo(createGoodsDto.getGoodsInfo())
                           .salesStatus(SalesStatus.SALE)
                           .goodImage(mainImagePath).build();

        Goods newGoods = goodsRepository.save(goods);

        if(newGoods == null){
            throw new CustomException(ErrorCode.NOT_FOUND_GOODS);
        }

        for(MultipartFile file : createGoodsDto.getDetailImage()){


            String detailFile = FileManger.saveGoodsDetail(user.getId(), file);

            GoodsImage goodsImage = GoodsImage.builder().goodsId(newGoods.getId()).goodImage(detailFile).build();
            if(goodsImage != null){
                goodsDetailRepository.save(goodsImage);
            }else{
                throw new CustomException(ErrorCode.NOT_FOUND_GOODS);
            }
        }
    }

    // 오름차순 최신순
    public List<ShowGoods> findGoodsByCreatedDesc(){

        List<ShowGoods> showGoodsList = new ArrayList<>();
        List<Goods> goodsList = goodsRepository.findAllByOrderByCreatedAtDesc();

        for(Goods goods : goodsList){
            List<GoodsImage> goodsImageList = goodsDetailRepository.findByGoodsId(goods.getId());

            ShowGoods showGoods = ShowGoods.builder()
                                           .goodsId(goods.getId())
                                           .title(goods.getTitle())
                                           .price(goods.getPrice())
                                           .location(goods.getLocation())
                                           .goodsInfo(goods.getGoodInfo())
                                           .mainImage(goods.getGoodImage())
                                           .createdAt(goods.getCreatedAt())
                                           .salesStatus(goods.getSalesStatus())
                                           .formattedCreatedAt(ShowGoods.calculateTime(goods.getCreatedAt()))
                                           .detailImage(goodsImageList)
                                           .build();

            showGoodsList.add(showGoods);
        }

        return showGoodsList;

    }
    //


}
