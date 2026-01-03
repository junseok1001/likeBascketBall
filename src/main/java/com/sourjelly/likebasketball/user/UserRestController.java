package com.sourjelly.likebasketball.user;


import com.sourjelly.likebasketball.user.domain.UserStatus;
import com.sourjelly.likebasketball.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;


@RequestMapping("/user")
@RestController
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

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





}
