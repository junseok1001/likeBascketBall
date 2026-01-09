package com.sourjelly.likebasketball.user.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Properties;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoUserInfoDto {

    private Long id;
    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;

    private Properties properties;


    @Data
    public static class Properties{
        private String nickname;
    }

    @Data
    public static class KakaoAccount{
        private String email;
    }


}
