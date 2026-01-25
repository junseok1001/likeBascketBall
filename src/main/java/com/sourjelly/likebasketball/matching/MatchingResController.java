package com.sourjelly.likebasketball.matching;

import com.sourjelly.likebasketball.common.global.CustomException;
import com.sourjelly.likebasketball.common.global.ErrorCode;
import com.sourjelly.likebasketball.common.responseApi.ResponseApi;
import com.sourjelly.likebasketball.matching.dto.MatchStart;
import com.sourjelly.likebasketball.matching.service.MatchingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MatchingResController {

    private final MatchingService matchingService;
    // 매칭 신청하는 api
    @PostMapping("/match")
    public ResponseEntity<ResponseApi<Void>> matching(@Validated MatchStart matchStart){

        if(matchStart == null){
            throw new CustomException(ErrorCode.NOT_DATA_IN_FIELD);
        }

        matchingService.matching(matchStart);


        return ResponseEntity.ok(ResponseApi.success("성공"));
    }

    // 매칭 거절 기능
    // 기능을 두개를 따로 만들어야 되나??? api로 확장성으로는 나누는게 맞는데 여기서만 쓰일거 같음 애매하네
    @PostMapping("/match/status")
    public ResponseEntity<ResponseApi<Void>> rejectApi(@RequestParam long matchingId, @RequestParam String status){

        if(matchingService.changeMatchStatus(matchingId, status)){
            return ResponseEntity.ok(ResponseApi.success("경기 수락 거절기능 성공"));
        }else{
            throw new CustomException(ErrorCode.PARAMETER_NOT_FOUND);
        }

    }


    // 매칭 수락 기능




}
