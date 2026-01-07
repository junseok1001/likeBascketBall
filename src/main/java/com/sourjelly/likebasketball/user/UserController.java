package com.sourjelly.likebasketball.user;


import com.sourjelly.likebasketball.user.dto.KakaoUserInfoDto;
import com.sourjelly.likebasketball.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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

    @PostMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();

        return "redirect:/main";
    }



    @GetMapping("/kakao/callback")
    public String kakaoLogin(
            @RequestParam String code
            , HttpSession session
            , RedirectAttributes redirectAttributes){
        // 인증된 코드로 토큰 발행하기
        String accessToken = userService.getAccessToken(code);
        System.out.println("발급된 토큰: " + accessToken);
        KakaoUserInfoDto userInfo = userService.getUserInfo(accessToken);

        // ------------------------------------------------------------------
        // 만약 내 db에 회원정보가 없다면 (식별자 email)로 회원가입 진행 이메일(소셜 로그인)
            if(userService.SocialLogin(userInfo.getKakaoAccount().getEmail()) != null){
                session.setAttribute("user", userService.SocialLogin(userInfo.getKakaoAccount().getEmail()));
                return "redirect:/main";
            }else{
                // 토큰으로 사용자 정보를 받아오기
                redirectAttributes.addAttribute("nickname", userInfo.getProperties().getNickname());
                redirectAttributes.addAttribute("email", userInfo.getKakaoAccount().getEmail());
                redirectAttributes.addAttribute("provider", "Kakao");

                return "redirect:/user/join";
            }
    }


}
