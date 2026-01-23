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
import com.sourjelly.likebasketball.matching.dto.SendModalMatch;
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

    // 매칭 신청 기능 완료
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
    // 경기 신청할때 club 정보 가져오기
    public List<ShowClubDto> clubList(){
        return clubService.getClubInfo();
    }

    // 받은 경기 정보 가져오기
    public List<SendMatching> incomingMatch(long userId){

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



    // 보낸 경기 정보 가져오기
    public List<SendMatching>sentMatch(long userId){
        List<SendMatching> sendMatchingList = new ArrayList<>();
        // 내 아이디에 있는 고유 번호로 클럽 찾기
        SelectClubDto selectClubDto = clubService.findMyClub(userId);

        // 클럽 아이디로
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

    // 모달에 정보 주는 기능
    public SendModalMatch MatchingInfoByMatchingId(long matchId){
        // 매칭 정보 가져오기
        Matching matching = matchingRepository.findById(matchId)
                                            .orElseThrow(() -> new CustomException(ErrorCode.NO_MATCHING));

        long challengeClubId = matching.getChallengeClub();
        long awayClubId = matching.getAwayClub();



        SelectClubDto challengeClubInfo = clubService.findClub(challengeClubId);
        SelectClubDto awayClubInfo = clubService.findClub(awayClubId);

        SendModalMatch matchingInfo = SendModalMatch.builder()
                                                .awayClubId(awayClubInfo.getId())
                                                .awayClubProfilePath(awayClubInfo.getProfileImage())
                                                .awayClubName(awayClubInfo.getClubName())
                                                .challengeClubId(challengeClubInfo.getId())
                                                .challengeClubProfilePath(challengeClubInfo.getProfileImage())
                                                .challengeClubName(challengeClubInfo.getClubName())
                                                .matchingId(matching.getId())
                                                .gameDate(matching.getGameDate())
                                                .gameTime(matching.getGameTime())
                                                .matchStatus(matching.getMatchStatus())
                                                .locationName(matching.getLocationName())
                                                .location(matching.getLocation())
                                                .content(matching.getContent())
                                                .phoneNumber(matching.getPhoneNumber())
                                                .build();

        return matchingInfo;
    }

    public boolean changeMatchStatus(long matchId, String status){

        Matching matching = matchingRepository.findById(matchId)
                .orElseThrow(() -> new CustomException(ErrorCode.NO_MATCHING));


        if(status.equals("submit")){
            matching.toBuilder().matchStatus(MatchStatus.SUBMIT).build();
            return true;
        }else if(status.equals("reject")){
            matching.toBuilder().matchStatus(MatchStatus.REJECT).build();
            return true;
        }

        return false;
    }


}
