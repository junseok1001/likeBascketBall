package com.sourjelly.likebasketball.user.service;


import com.sourjelly.likebasketball.user.domain.User;
import com.sourjelly.likebasketball.user.domain.UserStatus;
import com.sourjelly.likebasketball.user.dto.KakaoUserInfoDto;
import com.sourjelly.likebasketball.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
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
        log.info("전송되는 clientId: [" + clientId + "]");
        log.info("전송되는 redirectUri: [" + redirectUri + "]");
        log.info("전송되는 code: [" + code + "]");

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

    public User SocialLogin(String email){
        User user = userRepository.findByEmail(email);

        if(user == null){
            return null;
        }else{
            return user;
        }
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
            , String provider
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
                .provider(provider)
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

    // 회원정보 수정
    public User changeUserInfo(long id, String password , String nickname, String phoneNumber){

       Optional<User> optionalUser = userRepository.findById(id);
       String encodeingPassword = passwordEncoder.encode(password);

       if(optionalUser.isPresent()){
            User user = optionalUser.get();
            user = user.toBuilder().password(encodeingPassword).nickName(nickname).phoneNumber(phoneNumber).build();
            User modifyUser = userRepository.save(user);

            return modifyUser;
       }
       return null;

    }


    public void upgradeUser(User user){
        userRepository.save(user);
    }
}
