package com.sourjelly.likebasketball.matching.domain;

import lombok.Getter;

@Getter
public enum MatchStatus {

//    'SUBMIT', 'REJECT', 'WAITING'

    SUBMIT // 수락
    , REJECT // 거절
    , WAITING // 대기
}
