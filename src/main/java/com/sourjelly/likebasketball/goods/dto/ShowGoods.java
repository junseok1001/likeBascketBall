package com.sourjelly.likebasketball.goods.dto;

import com.sourjelly.likebasketball.goods.doamin.GoodsImage;
import com.sourjelly.likebasketball.goods.doamin.SalesStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Getter
@Builder(toBuilder = true)
public class ShowGoods {

    private long goodsId;
    private String title;
    private int price;
    private String location;
    private String goodsInfo;
    private String mainImage;
    private SalesStatus salesStatus;
    private LocalDateTime createdAt;      // 원본 시간 데이터 (정렬이나 상세 비교용)
    private String formattedCreatedAt;    // 가공된 시간 데이터 (화면 표시용: "n분 전")
    private List<GoodsImage> detailImage;

    /*
     * createdAt 받아서 시간 비교해주기
     * "방금 전", "n분 전", "n시간 전", "n일 전" 으로 나누기
     */
    public static String calculateTime(LocalDateTime createdAt) {
        if (createdAt == null) {
            return "";
        }

        //현새기간 가져오기
        LocalDateTime now = LocalDateTime.now();
        // 초/나노초로 관리해주는 객체생성 및 createdAt과 현재 나노초 비교해서 객체에 저장
        Duration duration = Duration.between(createdAt, now);
        // 비교된 객체를 초로 변환 해서 저장하기
        long seconds = duration.getSeconds();


        //비교된 초를 기준으로 60초 이전에 방금전 문자열 리턴
        if (seconds < 60) {
            return "방금 전";
        } else if (seconds < 3600) { // 3600초(60분) 미만
            return (seconds / 60) + "분 전";
        } else if (seconds < 86400) { // 86400초(24시간) 미만
            return (seconds / 3600) + "시간 전";
        } else {
            // 날짜 차이 계산 (LocalDate로 변환하여 '일' 단위로 정확히 계산)
            // createdAt LocalDateTime을 LocalDate로 변환해서 일자만 빼기
            // 현재 일자랑 createdAt일자를 비교해서 일에관한 정보 빼기
            long days = ChronoUnit.DAYS.between(createdAt.toLocalDate(), now.toLocalDate());

            if (days < 31) { //31일
                return days + "일 전";
            } else if (days < 365) { //365일 이전이라면 몇개월전으로 기준
                return (days / 30) + "개월 전";
            } else {
                return (days / 365) + "년 전";
            }
        }
    }
}
