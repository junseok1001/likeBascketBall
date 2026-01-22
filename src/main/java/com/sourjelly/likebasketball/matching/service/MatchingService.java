package com.sourjelly.likebasketball.matching.service;


import com.sourjelly.likebasketball.club.domain.Club;
import com.sourjelly.likebasketball.club.dto.SelectClubDto;
import com.sourjelly.likebasketball.club.dto.ShowClubDto;
import com.sourjelly.likebasketball.club.service.ClubService;
import com.sourjelly.likebasketball.common.global.CustomException;
import com.sourjelly.likebasketball.common.global.ErrorCode;
import com.sourjelly.likebasketball.matching.domain.MatchStatus;
import com.sourjelly.likebasketball.matching.domain.Matching;
import com.sourjelly.likebasketball.matching.dto.MatchStart;
import com.sourjelly.likebasketball.matching.dto.SendMatching;
import com.sourjelly.likebasketball.matching.repository.MatchingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
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

        Matching matching = Matching.builder().challengeClub(matchStart.getChallengeClubId())
                    .awayClub(matchStart.getAwayClubId())
                    .gameDate(matchStart.getGameDate())
                    .gameTime(matchStart.getGameTime())
                    .matchStatus(MatchStatus.WAITING)
                    .locationName(matchStart.getLocationName())
                    .location(matchStart.getLocation())
                    .content(matchStart.getContent())
                    .phoneNumber(matchStart.getPhoneNumber())
                    .build();

        matchingRepository.save(matching);
    }

    public List<ShowClubDto> clubList(){
        return clubService.getClubInfo();
    }

    public List<SendMatching> incomingMatch(long userId){

        List<SendMatching> sendMatchingList = new ArrayList<>();
        SelectClubDto selectClubDto = clubService.findMyClub(userId);

        List<Matching> matchingList = matchingRepository.findByChallengeClub(selectClubDto.getId());

        for(Matching matching : matchingList){

            SelectClubDto awayClubInfo = clubService.findClub(matching.getAwayClub());
            SendMatching sendMatching = SendMatching.builder()
                                                    .challengeClubId(selectClubDto.getId())
                                                    .challengeClubName(selectClubDto.getClubName())
                                                    .challengeClubProfilePath(selectClubDto.getProfileImage())
                                                    .awayClubId(awayClubInfo.getId())
                                                    .awayClubName(awayClubInfo.getClubName())
                                                    .awayClubProfilePath(awayClubInfo.getProfileImage())
                                                    .matchingId(matching.getId()).gameDate(matching.getGameDate())
                                                    .gameTime(matching.getGameTime()).matchStatus(matching.getMatchStatus())
                                                    .locationName(matching.getLocationName())
                                                    .location(matching.getLocation()).phoneNumber(matching.getPhoneNumber())
                                                    .build();
            sendMatchingList.add(sendMatching);
        }

        return sendMatchingList;
    }




    public List<SendMatching>sentMatch(long userId){
        List<SendMatching> sendMatchingList = new ArrayList<>();
        // 내 정보로 내 클럽 정보 가져오기
        SelectClubDto selectClubDto = clubService.findMyClub(userId);

        // 클럽 정보로 매칭 정보 가져오기
        List<Matching> matchingList = matchingRepository.findByAwayClub(selectClubDto.getId());
        // 매칭 정보로 클럽 정보들 가져와서 dto에 담기
        for(Matching matching : matchingList){
            SelectClubDto awayClubInfo = clubService.findClub(matching.getChallengeClub());
            // 나머지 정보들 담기
            SendMatching sendMatching = SendMatching.builder()
                                                    .challengeClubId(selectClubDto.getId())
                                                    .challengeClubName(selectClubDto.getClubName())
                                                    .challengeClubProfilePath(selectClubDto.getProfileImage())
                                                    .awayClubId(awayClubInfo.getId())
                                                    .awayClubName(awayClubInfo.getClubName())
                                                    .awayClubProfilePath(awayClubInfo.getProfileImage())
                                                    .matchingId(matching.getId()).gameDate(matching.getGameDate())
                                                    .gameTime(matching.getGameTime()).matchStatus(matching.getMatchStatus())
                                                    .locationName(matching.getLocationName())
                                                    .location(matching.getLocation()).phoneNumber(matching.getPhoneNumber())
                                                    .build();
            sendMatchingList.add(sendMatching);
        }

        return sendMatchingList;
    }


}
