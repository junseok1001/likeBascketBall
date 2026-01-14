package com.sourjelly.likebasketball.main;


import com.sourjelly.likebasketball.club.dto.ShowClubDto;
import com.sourjelly.likebasketball.main.service.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final MainService mainService;

    @RequestMapping("/main")
    public String mainPage(Model model){

        List<ShowClubDto> showClubDtoList = mainService.getClubs();
        model.addAttribute("clubs", showClubDtoList);

        return "/main/index";
    }

}
