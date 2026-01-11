package com.sourjelly.likebasketball.club;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClubController {


    @GetMapping("/club")
    public String club(){
        return "/clubForm/clubform";
    }


}
