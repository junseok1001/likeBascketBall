package com.sourjelly.likebasketball.matching.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Getter
public class MatchStart {

//    let awayClub = $("#awayClub").val();
//    let gameDate = $("#gameDate").val();
//    let gameTime = $("#gameTime").val();
//    let locationName = $("input[name='locationName']").val();
//    let location = $("input[name='location']").val();
//    let content = $("textarea[name='content']").val();
//    let phoneNumber =$("#phoneNumberInput").val();

    private long challengeClubId;
    @NotBlank(message="도전하는팀이 비었습니다.")
    private String challengeClub;
    private long awayClubId;
    @NotBlank(message="어웨이팀이 비었습니다.")
    private String awayClub;
    @NotNull(message="이거 뭐야?")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate gameDate;
    @NotNull(message="저건 뭐고?")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime gameTime;
    @NotBlank
    private String locationName;
    @NotBlank(message="위치가 비었습니다.")
    private String location;
    private String content;
    @NotBlank(message="연락처가 비었습니다.")
    private String phoneNumber;


}
