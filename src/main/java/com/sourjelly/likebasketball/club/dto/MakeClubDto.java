package com.sourjelly.likebasketball.club.dto;

import com.sourjelly.likebasketball.club.domain.Club;
import com.sourjelly.likebasketball.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Getter
@Builder(toBuilder = true)
public class MakeClubDto {


    private String clubName;
    private String password;
    private String meetingDay;
    private String meetingTime;
    private String activityArea;
    private String introduce;
    private String phoneNumber;
    private String price;
    private MultipartFile profileImage;

    private List<MultipartFile> images;

    // 동호회 생성하는 entity만들기
    public Club toEntity(User user, String encodingPassword, String profilePath){
        return Club.builder()
                .userId(user.getId())
                .clubName(this.clubName)
                .password(encodingPassword)
                .meetingDay(this.meetingDay)
                .meetingTime(this.meetingTime)
                .activityArea(this.activityArea)
                .introduce(this.introduce)
                .phoneNumber(this.phoneNumber)
                .price(this.price)
                .profileImage(profilePath )
                .build();
    }

    public Club toEntity(User user, String encodingPassword){
        return Club.builder()
                .userId(user.getId())
                .clubName(this.clubName)
                .password(encodingPassword)
                .meetingDay(this.meetingDay)
                .meetingTime(this.meetingTime)
                .activityArea(this.activityArea)
                .introduce(this.introduce)
                .phoneNumber(this.phoneNumber)
                .price(this.price)
                .build();
    }

}
