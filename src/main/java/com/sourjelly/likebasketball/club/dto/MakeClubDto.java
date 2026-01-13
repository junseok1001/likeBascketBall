package com.sourjelly.likebasketball.club.dto;

import com.sourjelly.likebasketball.club.domain.Club;
import com.sourjelly.likebasketball.club.domain.ClubPhoto;
import com.sourjelly.likebasketball.user.domain.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Getter
@Builder(toBuilder = true)
public class MakeClubDto {

    @NotBlank(message = "클럽이름은 필수입니다.")
    private String clubName;

    @NotBlank(message = "클럽에 들어가기위한 비밀번호는 필수입니다.")
    private String password;

    private String meetingDay;
    private String meetingTime;

    @NotBlank(message = "클럽 활동지역은 필수입니다.")
    private String activityArea;
    private String introduce;

    private String phoneNumber;

    @NotBlank(message = "월 회비는 필수입니다.")
    private String price;
    private List<String> imagePath;

    // 동호회 생성하는 entity만들기
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
