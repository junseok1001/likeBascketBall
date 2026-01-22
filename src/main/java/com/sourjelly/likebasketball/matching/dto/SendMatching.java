package com.sourjelly.likebasketball.matching.dto;

import com.sourjelly.likebasketball.matching.domain.MatchStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Builder(toBuilder = true)
public class SendMatching {

    private long awayClubId;
    private String awayClubName;
    private String awayClubProfilePath;

    private long challengeClubId;
    private String challengeClubName;
    private String challengeClubProfilePath;

    private long matchingId;
    private LocalDate gameDate;
    private LocalTime gameTime;
    @Enumerated(EnumType.STRING)
    private MatchStatus matchStatus;
    private String location;
    private String phoneNumber;
}
