package com.sourjelly.likebasketball.club;

import com.sourjelly.likebasketball.club.dto.MakeClubDto;
import com.sourjelly.likebasketball.club.service.ClubService;
import com.sourjelly.likebasketball.common.responseApi.ResponseApi;
import com.sourjelly.likebasketball.user.domain.User;
import com.sourjelly.likebasketball.user.domain.UserStatus;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ClubRestController {

    private final ClubService clubService;

    @PostMapping("/club")
    public ResponseEntity<ResponseApi<Void>> creatClub(
            @ModelAttribute MakeClubDto makeClubDto
            , HttpSession session
            ){

        User user = (User)session.getAttribute("user");

        if(clubService.creatClub(makeClubDto, user)){
            return ResponseEntity.ok(ResponseApi.success("성공"));
        }

        return ResponseEntity.ok(ResponseApi.fail("실패"));
    }

    @PostMapping("/test")
    public List<String> test(
            @ModelAttribute MakeClubDto makeClubDto){
        return makeClubDto.getImagePath();
    }

}
