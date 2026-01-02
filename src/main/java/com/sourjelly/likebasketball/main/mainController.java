package com.sourjelly.likebasketball.main;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class mainController {


    @RequestMapping("/main")
    public String mainPage(){
        return "/main/index";
    }

}
