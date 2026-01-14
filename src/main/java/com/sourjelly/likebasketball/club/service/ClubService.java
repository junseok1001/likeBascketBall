package com.sourjelly.likebasketball.club.service;


import com.sourjelly.likebasketball.club.domain.Club;
import com.sourjelly.likebasketball.club.domain.ClubPhoto;
import com.sourjelly.likebasketball.club.dto.MakeClubDto;
import com.sourjelly.likebasketball.club.dto.ShowClubDto;
import com.sourjelly.likebasketball.club.repository.ClubMemberRepository;
import com.sourjelly.likebasketball.club.repository.ClubPhotoRepository;
import com.sourjelly.likebasketball.club.repository.ClubRepository;
import com.sourjelly.likebasketball.common.fileManger.FileManger;
import com.sourjelly.likebasketball.user.domain.User;
import com.sourjelly.likebasketball.user.domain.UserStatus;
import com.sourjelly.likebasketball.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClubService {

    private final UserService userService;
    private final ClubRepository clubRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final ClubPhotoRepository clubPhotoRepository;
    private final PasswordEncoder passwordEncoder;


    public boolean creatClub(
            MakeClubDto makeClubDto
            , User user){

        String encodingPassword = passwordEncoder.encode(makeClubDto.getPassword());
        List<String> filesPath = FileManger.saveFiles(user.getId(), makeClubDto.getImagePath());
        try{
            Club newClub = makeClubDto.toEntity(user, encodingPassword);
            Club club = clubRepository.save(newClub);
            long clubId = club.getId();

            for(String filePath : filesPath){
                ClubPhoto clubPhoto = ClubPhoto.builder().clubId(clubId).imagePath(filePath).build();
                clubPhotoRepository.save(clubPhoto);
            }

        }catch(DataAccessException e){
            // 생성 실패
            return false;
        }
        // 생성 완료
        User upgradeUser = user.toBuilder().userStatus(UserStatus.CLUB_PERSISTENT).build();
        userService.upgradeUser(upgradeUser);
        return true;
    }


    // club리스트 가져오기
    public List<ShowClubDto> getClubInfo(){
        List<Club> clubList = clubRepository.findAll();

        List<ShowClubDto> showClubDtoList = new ArrayList<>();
        for(Club showClub : clubList){
            List<ClubPhoto> clubPhotoList = clubPhotoRepository.findById(showClub.getId());

            List<String> imagePaths = new ArrayList<>();
            for(ClubPhoto clubPhoto : clubPhotoList){
                imagePaths.add(clubPhoto.getImagePath());
            }
            showClubDtoList.add(ShowClubDto.builder()
                    .clubName(showClub.getClubName())
                    .meetingDay(showClub.getMeetingDay())
                    .meetingTime(showClub.getMeetingTime())
                    .activityArea(showClub.getActivityArea())
                    .introduce(showClub.getIntroduce())
                    .phoneNumber(showClub.getPhoneNumber())
                    .imagePath(imagePaths).build());
        }
        return showClubDtoList;
    }


}
