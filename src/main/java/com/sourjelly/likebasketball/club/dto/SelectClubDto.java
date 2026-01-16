package com.sourjelly.likebasketball.club.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Builder
public class SelectClubDto {

    private long id;
    private String clubName;
    private String activityArea;
    private String meetingDay;
    private String meetingTime;
    private String introduce;
    private String price;
    private String phoneNumber;
    private String password;
    private String profileImage;
}
