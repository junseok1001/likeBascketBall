package com.sourjelly.likebasketball.club.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder(toBuilder = true)
public class ClubActivePhoto {

    private long id;
    private String imagePath;
}
