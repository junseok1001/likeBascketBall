package com.sourjelly.likebasketball.matching.dto;

import com.sourjelly.likebasketball.matching.domain.MatchStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Builder(toBuilder = true)
public class ApplyMatching {

    private long awayClubId;
    private String awayClubName;
    private String awayClubProfilePath;

    private long challengeClubId;
    private String challengeClubName;
    private String challengeClubProfilePath;

    private long matchingId;
    private LocalDate gameDate;
    private LocalTime gameTime;
    private MatchStatus matchStatus;
    private String location;
    private String phoneNumber;

}
