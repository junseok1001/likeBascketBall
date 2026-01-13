package com.sourjelly.likebasketball.user;


import com.sourjelly.likebasketball.user.dto.KakaoUserInfoDto;
import com.sourjelly.likebasketball.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/user")
@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    // 회원가입 페이지 이동
    @GetMapping("/join")
    public String joinPage(){
        return "/joinForm/joinForm";
    }
    // 로그인 페이지 이동
    @GetMapping("/login")
    public String loginPage(){
        return "/login/login";
    }
    // 로그아웃 후 메인페이지 이동
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();

        return "redirect:/main";
    }
    // 회원정보 상세 페이지 이동
    @GetMapping("/detail")
    public String modify(){
        return "/modify/detail";
    }


    // 카카오톡 로그인 콜백 후 redirect
    @GetMapping("/kakao/callback")
    public String kakaoLogin(
            @RequestParam String code
            , HttpSession session){
        // 인증된 코드로 토큰 발행하기
        String accessToken = userService.getAccessToken(code);
        // log 를 손쉽게 찍을수 있게 System.out.println ->
        log.info("발급된 토큰: " + accessToken);
        KakaoUserInfoDto userInfo = userService.getUserInfo(accessToken);

        // ------------------------------------------------------------------
        // 만약 내 db에 회원정보가 없다면 (식별자 email)로 회원가입 진행 이메일(소셜 로그인)
            if(userService.SocialLogin(userInfo.getKakaoAccount().getEmail()) != null){
                session.setAttribute("user", userService.SocialLogin(userInfo.getKakaoAccount().getEmail()));
                return "redirect:/main";
            }else{
                // 1회성 데이터 저장 - session
                session.setAttribute("nickname", userInfo.getProperties().getNickname());
                session.setAttribute("email", userInfo.getKakaoAccount().getEmail());
                session.setAttribute("provider", "Kakao");

                return "redirect:/user/join";
            }
    }


}
