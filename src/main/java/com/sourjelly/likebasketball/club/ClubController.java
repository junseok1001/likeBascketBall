package com.sourjelly.likebasketball.club;

import com.sourjelly.likebasketball.club.service.ClubService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class ClubController {

    private final ClubService clubService;

    @GetMapping("/club")
    public String club(){
        return "/clubForm/clubform";
    }

    @GetMapping("/club/info/{userId}")
    public String info(
            @PathVariable long userId
            , Model model){

        model.addAttribute("club",clubService.findMyClub(userId));
        return "/clubForm/clubInfo";
    }

//   추후 더 만들 페이지
    @GetMapping("/club/detail")
    public String detail(Model model){


        return "/clubForm/clubDetail";
    }






}
