package com.sourjelly.likebasketball.user.service;


import com.sourjelly.likebasketball.user.domain.User;
import com.sourjelly.likebasketball.user.domain.UserStatus;
import com.sourjelly.likebasketball.user.dto.KakaoUserInfoDto;
import com.sourjelly.likebasketball.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final WebClient webClient = WebClient.builder().build();

    @Value("${kakao.apiKey}")
    private String clientId;

    @Value("${kakao.redirect-uri}")
    private String redirectUri;

    @Value("${kakao.client-secret}")
    private String clientSecret;

    // 카카오톡 access token 요청
    public String getAccessToken(String code){
        System.out.println("전송되는 clientId: [" + clientId + "]");
        System.out.println("전송되는 redirectUri: [" + redirectUri + "]");
        System.out.println("전송되는 code: [" + code + "]");

        return webClient.post()
                .uri("https://kauth.kakao.com/oauth/token")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .body(BodyInserters.fromFormData("grant_type", "authorization_code")
                        .with("client_id", clientId)
                        .with("redirect_uri", redirectUri)
                        .with("code", code)
                        .with("client_secret", clientSecret))
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> response.get("access_token").toString())
                .block();
    }

    //카카오톡 사용자 정보 요청
    public KakaoUserInfoDto getUserInfo(String accessToken){
        return webClient.get()
                .uri("https://kapi.kakao.com/v2/user/me")
                .header(HttpHeaders.AUTHORIZATION,"Bearer " + accessToken)
                .retrieve()
                .bodyToMono(KakaoUserInfoDto.class)
                .block();
    }

    //회원가입 기능
    public boolean insertMember(
            String loginId
            , String password
            , String nickName
            , UserStatus userStatus
            , LocalDate birthday
            , String phoneNumber
            , String email
    ){
        String encodingPassword = passwordEncoder.encode(password);

        User user = User.builder()
                .loginId(loginId)
                .password(encodingPassword)
                .userStatus(userStatus)
                .nickName(nickName)
                .birthday(birthday)
                .phoneNumber(phoneNumber)
                .email(email)
                .build();

        try{
            userRepository.save(user);
        }catch(DataAccessException e){
            return false;
        }

        return true;
    }

    public boolean isDuplicateId(String loginId){

        return !userRepository.existsByLoginId(loginId);
    }

    // 로그인 기능
    public User isExistId(
            String loginId
            , String password){

        User user = userRepository.findByLoginId(loginId);
        String encodingPassword = user.getPassword();

        if(user != null && passwordEncoder.matches(password, encodingPassword)){
            return user;
        }

        return null;
    }



}
