package com.sourjelly.likebasketball.matching.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(toBuilder = true)
public class ClubInfo {

    private long clubId;
    private String clubName;

}
