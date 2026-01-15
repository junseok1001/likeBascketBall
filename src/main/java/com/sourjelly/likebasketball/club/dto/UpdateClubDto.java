package com.sourjelly.likebasketball.club.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateClubDto {

    private long id;
    private String clubName;
    private String activityArea;
    private String meetingDay;
    private String meetingTime;
    private String introduce;
    private String price;
    private String phoneNumber;
    private String password;
    private MultipartFile profileImage;
}
