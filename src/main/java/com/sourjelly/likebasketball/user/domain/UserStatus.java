package com.sourjelly.likebasketball.user.domain;


import lombok.Getter;

@Getter
public enum UserStatus {

    RENTER, // 대여자
    GENERAL, // 일반 사용자
    CLUB_PERSISTENT // 동호회장
}
