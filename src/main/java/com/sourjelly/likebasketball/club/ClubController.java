package com.sourjelly.likebasketball.club;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClubController {


    @GetMapping("/club")
    public String club(){
        return "/clubForm/clubform";
    }

    @GetMapping("/club/info")
    public String info(){
        return "/clubForm/clubInfo";
    }

    @GetMapping("/club/detail")
    public String detail(Model model){


        return "/clubForm/clubDetail";
    }


}
