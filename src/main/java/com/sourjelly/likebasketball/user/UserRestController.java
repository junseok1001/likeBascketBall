package com.sourjelly.likebasketball.user;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;


@RequestMapping("/user")
@RestController
@RequiredArgsConstructor
public class UserRestController {


    private final PasswordEncoder passwordEncoder;

    @PostMapping("/join")
    public String join(
            @RequestParam String loginId
            , @RequestParam String password
            , @RequestParam String nickName
            , @RequestParam String userStatus
            , @RequestParam LocalDate birthDay
            , @RequestParam String phoneNumber){




    }





}
