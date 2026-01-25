package com.sourjelly.likebasketball.common.global;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // 400 Bad Request
    INVALID_PARAMETER(400,  "잘못된 요청 파라미터입니다.")
    // 404 notFound
    , PARAMETER_NOT_FOUND(404, "요청된 정보가 잘못되었습니다.")
    , CLUB_NOT_FOUND(404, "클럽을 찾을수 없습니다.")

    // 500 INTERNAL_SERVER_ERROR
    , INTERNAL_SERVER_ERROR(500, "서버에서 처리하는도중 오류가 났습니다.")
    , USER_NOT_FOUND(404, "유저를 찾을수 없습니다. 로그인 하세요")
    , NOT_DATA_IN_FIELD(404, "DTO가 비었습니다")
    , NO_MATCHING(404, "경기를 찾을수 없습니다.")
    , ALREADY_SUBMIT(202 , "이미 수락되었습니다")
    , ALREADY_REJECT(202 , "이미 거절되었습니다")
    ;

    private final int status;
    private final String message;
}
