package com.sourjelly.likebasketball.club.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Builder(toBuilder = true)
public class ShowClubDto {


    private String clubName;

    private String meetingDay;
    private String meetingTime;


    private String activityArea;
    private String introduce;

    private String phoneNumber;


    private String price;

    private List<String> imagePath;
}
