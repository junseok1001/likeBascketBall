package com.sourjelly.likebasketball.club;

import com.sourjelly.likebasketball.club.dto.MakeClubDto;
import com.sourjelly.likebasketball.club.dto.UpdateClubDto;
import com.sourjelly.likebasketball.club.service.ClubService;
import com.sourjelly.likebasketball.common.responseApi.ResponseApi;
import com.sourjelly.likebasketball.user.domain.User;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequiredArgsConstructor
public class ClubRestController {

    private final ClubService clubService;
    //클럽생성
    @PostMapping("/club")
    public ResponseEntity<ResponseApi<Void>> creatClub(
            @ModelAttribute MakeClubDto makeClubDto
            , HttpSession session
    ){
        log.info("가져오는 데이터 :{}" ,makeClubDto);
        User user = (User)session.getAttribute("user");
        if(clubService.creatClub(makeClubDto, user)){
            //생성 완료
            return ResponseEntity.ok(ResponseApi.success("성공"));
        }
        return ResponseEntity.ok(ResponseApi.fail("실패"));
    }


    @PostMapping("/club/update")
    public ResponseEntity<ResponseApi<Void>> updateClubInfo(
            @ModelAttribute UpdateClubDto updateClubDto
    ){
        log.info("가져오는값 : {}", updateClubDto.getId());
        log.info("가져오는값 : {}", updateClubDto.getClubName());
        log.info("가져오는값 : {}", updateClubDto.getActivityArea());
        log.info("가져오는값 : {}", updateClubDto.getMeetingDay());
        log.info("가져오는값 : {}", updateClubDto.getMeetingTime());
        log.info("가져오는값 : {}", updateClubDto.getIntroduce());
        log.info("가져오는값 : {}", updateClubDto.getPrice());
        log.info("가져오는값 : {}", updateClubDto.getPhoneNumber());
        log.info("가져오는값 : {}", updateClubDto.getPassword());
        log.info("가져오는값 : {}", updateClubDto.getProfileImage());




        return ResponseEntity.ok(ResponseApi.success("성공"));
    }



}
