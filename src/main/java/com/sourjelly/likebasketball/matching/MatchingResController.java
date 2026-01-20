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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MatchingResController {

    private final MatchingService matchingService;

//    @PostMapping
//    public ResponseEntity<ResponseApi<Void>> matching(@Validated MatchStart matchStart){
//
//        if(matchStart == null){
//            throw new CustomException(ErrorCode.NOT_DATA_IN_FIELD);
//        }
//
//        matchingService.matching(matchStart);
//
//
//        return ResponseEntity.ok(ResponseApi.success("성공"));
//    }

    @PostMapping("/match")
    public MatchStart matching(@Validated MatchStart matchStart){

        log.info("데이터 : {}", matchStart.getGameDate());
        log.info("데이터 : {}", matchStart.getGameTime());

        return matchStart;
    }

}
