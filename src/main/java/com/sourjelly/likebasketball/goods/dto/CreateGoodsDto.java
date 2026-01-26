package com.sourjelly.likebasketball.goods.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Getter
@Builder(toBuilder = true)
public class CreateGoodsDto {

    @NotBlank(message = "제목은 필수입니다.")
    private String title;
    @NotNull(message = "가격은 필수입니다.")
    private int price;
    @NotBlank(message= "거래 위치는 필수 입니다.")
    private String location;
    @NotNull(message = "대표 이미지는 필수입니다.")
    private MultipartFile mainImage;
    private String goodsInfo;

    @Size(max=4, message="사진파일은 최대 4개 입니다.")
    private List<MultipartFile> detailImage;
}
