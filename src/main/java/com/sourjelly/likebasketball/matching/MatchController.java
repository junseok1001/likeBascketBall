package com.sourjelly.likebasketball.matching;

import com.sourjelly.likebasketball.club.domain.Club;
import com.sourjelly.likebasketball.club.dto.ShowClubDto;
import com.sourjelly.likebasketball.club.service.ClubService;
import com.sourjelly.likebasketball.common.global.CustomException;
import com.sourjelly.likebasketball.common.global.ErrorCode;
import com.sourjelly.likebasketball.common.global.GlobalExceptionHandler;
import com.sourjelly.likebasketball.matching.dto.ClubInfo;
import com.sourjelly.likebasketball.matching.service.MatchingService;
import com.sourjelly.likebasketball.user.domain.User;
import jakarta.servlet.http.HttpSession;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/match")
@RequiredArgsConstructor
public class MatchController {

    private final MatchingService matchingService;

    @GetMapping("/list")
    public String matchList(){



        return "/match/matchlist";
    }

    @GetMapping("/request")
    public String matchRequest(
            Model model
            , HttpSession session){


        User user = (User) session.getAttribute("user");
        // 인터셉터 생기면 지울거
        if(user == null){
            return "redirect:/main";
        }

        // 여기서 고민인거 --> 나는 null이 아니면 이걸 수행인데 만약 club이 널이면 에러 메시지와함께 돌려주는거 --> 일단 돌려주는건 interceptor가 하는 일인데 메세지를 주는건??
        ClubInfo myClub = ClubInfo.builder()
                                    .clubId(matchingService.ClubInfo(user.getId()).getId())
                                    .clubName(matchingService.ClubInfo(user.getId()).getClubName())
                                    .build();

        model.addAttribute("myClub", myClub);
        List<ClubInfo> clubInfoList = new ArrayList<>();
        List<ShowClubDto> clubLists = matchingService.clubList();
        for(ShowClubDto club : clubLists){

            ClubInfo clubInfo = ClubInfo.builder().clubId(club.getId()).clubName(club.getClubName()).build();
            clubInfoList.add(clubInfo);
        }
        model.addAttribute("clubList", clubInfoList);

        return "/match/matchrequest";
    }

//    @GetMapping("/detail/{matchId}")
//    public String modalView(@PathVariable long matchId){
//
//
//
//        return
//    }
}
