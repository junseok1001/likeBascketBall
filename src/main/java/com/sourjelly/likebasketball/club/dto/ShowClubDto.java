package com.sourjelly.likebasketball.club.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Builder(toBuilder = true)
public class ShowClubDto {

    private long Id;
    private String clubName;
    private String meetingDay;
    private String meetingTime;
    private String activityArea;
    private String introduce;
    private String phoneNumber;
    private String price;
    private String profileImage;

    private List<ClubActivePhoto> clubActivePhoto;
//    private long imagePathId;
//    private List<String> imagePath;
}
