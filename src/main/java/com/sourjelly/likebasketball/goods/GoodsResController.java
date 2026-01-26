package com.sourjelly.likebasketball.goods;


import com.sourjelly.likebasketball.common.global.CustomException;
import com.sourjelly.likebasketball.common.global.ErrorCode;
import com.sourjelly.likebasketball.common.responseApi.ResponseApi;
import com.sourjelly.likebasketball.goods.dto.CreateGoodsDto;
import com.sourjelly.likebasketball.goods.service.GoodsService;
import com.sourjelly.likebasketball.user.domain.User;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Slf4j
@RestController
@RequiredArgsConstructor
public class GoodsResController {

    private final GoodsService goodsService;

    @PostMapping("/goods")
    public ResponseEntity<ResponseApi<Void>> registerGoods(
            @Validated CreateGoodsDto createGoodsDto
            , HttpSession session){

        log.info(createGoodsDto.getTitle());
        log.info(createGoodsDto.getLocation());
        log.info("가격 :" + createGoodsDto.getPrice());
//        log.info("서브 이미지 : {}" + createGoodsDto.getDetailImage());
        if(session.getAttribute("user") == null){
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        User user = (User)session.getAttribute("user");

        goodsService.createGoods(user, createGoodsDto);

        return ResponseEntity.ok(ResponseApi.success("완료"));
    }



}
