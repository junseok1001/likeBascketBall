package com.sourjelly.likebasketball.matching.service;


import com.sourjelly.likebasketball.club.dto.SelectClubDto;
import com.sourjelly.likebasketball.club.service.ClubService;
import com.sourjelly.likebasketball.common.global.CustomException;
import com.sourjelly.likebasketball.common.global.ErrorCode;
import com.sourjelly.likebasketball.matching.domain.MatchStatus;
import com.sourjelly.likebasketball.matching.domain.Matching;
import com.sourjelly.likebasketball.matching.dto.MatchStart;
import com.sourjelly.likebasketball.matching.repository.MatchingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatchingService {

    private final ClubService clubService;
    private final MatchingRepository matchingRepository;

    // 로그인된 사용자의 정보를 가져오는 기능
    public SelectClubDto ClubInfo(long userId){
        return clubService.findMyClub(userId);
    }


    public void matching(MatchStart matchStart){

        Matching matching = Matching.builder().challengeClub(matchStart.getChallengeClubId()).awayClub(matchStart.getAwayClubId()).gameDate(matchStart.getGameDate()).gameTime(matchStart.getGameTime()).matchStatus(MatchStatus.WAITING).location(matchStart.getLocation()).content(matchStart.getContent()).phoneNumber(matchStart.getPhoneNumber()).build();
        matchingRepository.save(matching);
    }

}
