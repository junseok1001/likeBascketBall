package com.sourjelly.likebasketball.user;


import com.sourjelly.likebasketball.user.dto.KakaoUserInfoDto;
import com.sourjelly.likebasketball.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;

@RequestMapping("/user")
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/join")
    public String joinPage(){
        return "/joinForm/joinForm";
    }

    @GetMapping("/login")
    public String loginPage(){
        return "/login/login";
    }

    @GetMapping("/kakao/callback")
    public String kakaoLogin(
            @RequestParam String code){
        // 인증된 코드로 토큰 발행하기
        String accessToken = userService.getAccessToken(code);
        System.out.println("발급된 토큰: " + accessToken);

        // ------------------------------------------------------------------
        // 만약 내 db에 회원정보가 없다면 (식별자 email)로 회원가입 진행 이메일로

        // 토큰으로 사용자 정보를 받아오기
        KakaoUserInfoDto userInfo = userService.getUserInfo(accessToken);

        String redirectUrl = UriComponentsBuilder.fromPath("/user/join")
                .queryParam("email", userInfo.getKakaoAccount() .getEmail())
                .queryParam("nickname", userInfo.getProperties().getNickname())
                .build()
                .encode()
                .toUriString();

        return "redirect:" + redirectUrl;
    }


}
