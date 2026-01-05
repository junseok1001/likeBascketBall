package com.sourjelly.likebasketball.user;


import com.sourjelly.likebasketball.user.domain.User;
import com.sourjelly.likebasketball.user.domain.UserStatus;
import com.sourjelly.likebasketball.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;


@RequestMapping("/user")
@RestController
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;



    @GetMapping("/kakao/callback")
    public String kakaoLogin(
            @RequestParam String code
            , HttpSession session){
        // 인증된 코드로 토큰 발행하기
        String accessToken = userService.getAccessToken(code);
        System.out.println("성공! 발급된 토큰: " + accessToken);

        // 토큰으로 사용자 정보를 받아오기
        Map<String, Object> userInfo = userService.getUserInfo(accessToken);
        System.out.println("사용자 정보: " + userInfo);

        session.setAttribute("user", userInfo);
        return "로그인이 완료되었습니다";
    }

    @PostMapping("/join")
    public Map<String, String> join(
            @RequestParam String loginId
            , @RequestParam String password
            , @RequestParam String nickName
            , @RequestParam UserStatus userStatus
            , @RequestParam LocalDate birthday
            , @RequestParam String phoneNumber
            , @RequestParam String email){

        Map<String, String> resultMap = new HashMap<>();
        if(userService.insertMember(loginId, password, nickName, userStatus, birthday, phoneNumber, email)){
            resultMap.put("result", "success");
        }else{
            resultMap.put("result", "fail");
        }

        return resultMap;
    }

    @PostMapping("/isDuplicate")
    public Map<String, String> isDuplicateId(
            @RequestParam String loginId){

        Map<String, String> resultMap = new HashMap<>();
        if(userService.isDuplicateId(loginId)){
            resultMap.put("result", "success");
        }else{
            resultMap.put("result", "fail");
        }
        return resultMap;
    }

    @PostMapping("/login")
    public Map<String, String> login(
            @RequestParam String loginId
            , @RequestParam String password
            , HttpSession session){

        Map<String , String> resultMap = new HashMap<>();
        User user = userService.isExistId(loginId, password);

        if(user != null){
            resultMap.put("result", "success");
            session.setAttribute("user", user);
        }else{
            resultMap.put("result", "fail");
        }

        return resultMap;
    }

}
