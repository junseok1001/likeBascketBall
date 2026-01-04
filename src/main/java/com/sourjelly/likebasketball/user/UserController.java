package com.sourjelly.likebasketball.user;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/user")
@Controller
public class UserController {


    @GetMapping("/join")
    public String joinPage(){
        return "/joinForm/joinForm";
    }

    @GetMapping("/login")
    public String loginPage(){
        return "/login/login";
    }


}
