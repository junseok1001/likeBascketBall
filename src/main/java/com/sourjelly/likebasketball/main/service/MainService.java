package com.sourjelly.likebasketball.main.service;

import com.sourjelly.likebasketball.club.dto.SelectClubDto;
import com.sourjelly.likebasketball.club.dto.ShowClubDto;
import com.sourjelly.likebasketball.club.service.ClubService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MainService {

    private final ClubService clubService;

    public List<ShowClubDto> getClubs(){

        if(clubService.getClubInfo() != null){
            return clubService.getClubInfo();
        }else{
            return null;
        }
    }



}
