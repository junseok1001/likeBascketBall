package com.sourjelly.likebasketball.club.service;


import com.sourjelly.likebasketball.club.domain.Club;
import com.sourjelly.likebasketball.club.domain.ClubPhoto;
import com.sourjelly.likebasketball.club.dto.*;
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
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClubService {

    private final UserService userService;
    private final ClubRepository clubRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final ClubPhotoRepository clubPhotoRepository;
    private final PasswordEncoder passwordEncoder;

    //클럽 만들기
    public boolean creatClub(
            MakeClubDto makeClubDto
            , User user){



        String encodingPassword = passwordEncoder.encode(makeClubDto.getPassword());
        List<String> filesPath = FileManger.saveFiles(user.getId(), makeClubDto.getImages());
        String profileImage = FileManger.saveProfile(user.getId(), makeClubDto.getProfileImage());
        try{
            Club newClub = makeClubDto.toEntity(user, encodingPassword, profileImage);
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

            List<ClubActivePhoto> clubActivePhotoList  = new ArrayList<>();
            for(ClubPhoto clubPhoto : clubPhotoList){
                clubActivePhotoList.add(ClubActivePhoto.builder()
                                                                .id(clubPhoto.getId())
                                                                .imagePath(clubPhoto.getImagePath())
                                                                .build());
            }
            showClubDtoList.add(ShowClubDto.builder()
                    .Id(showClub.getId())
                    .clubName(showClub.getClubName())
                    .meetingDay(showClub.getMeetingDay())
                    .meetingTime(showClub.getMeetingTime())
                    .activityArea(showClub.getActivityArea())
                    .introduce(showClub.getIntroduce())
                    .phoneNumber(showClub.getPhoneNumber())
                    .clubActivePhoto(clubActivePhotoList)
                    .profileImage(showClub.getProfileImage())
                    .build());

        }
        return showClubDtoList;
    }

    // userId로 내가 가지고있는 club찾기
    public SelectClubDto findMyClub(long userId){
        Optional<Club> optionalClub =clubRepository.findByUserId(userId);

        if(optionalClub.isPresent()){
            Club selectClub = optionalClub.get();
            return SelectClubDto.builder()
                    .id(selectClub.getId())
                    .clubName(selectClub.getClubName())
                    .activityArea(selectClub.getActivityArea())
                    .meetingDay(selectClub.getMeetingDay())
                    .meetingTime(selectClub.getMeetingTime())
                    .introduce(selectClub.getIntroduce())
                    .price(selectClub.getPrice()).phoneNumber(selectClub.getPhoneNumber())
                    .profileImage(selectClub.getProfileImage())
                    .build();
        }else{
            return null;
        }
    }

    // clubId로 해당 club찾기
    public SelectClubDto findClub(long clubId){
        Optional<Club> optionalClub =clubRepository.findById(clubId);

        if(optionalClub.isPresent()){
            Club selectClub = optionalClub.get();
            return SelectClubDto.builder()
                        .id(selectClub.getId())
                        .clubName(selectClub.getClubName())
                        .activityArea(selectClub.getActivityArea())
                        .meetingDay(selectClub.getMeetingDay())
                        .meetingTime(selectClub.getMeetingTime())
                        .introduce(selectClub.getIntroduce())
                        .price(selectClub.getPrice()).phoneNumber(selectClub.getPhoneNumber())
                        .profileImage(selectClub.getProfileImage())
                        .build();
        }else{
            return null;
        }
    }

    //club id로 가져온 정보 수정하기
//    public boolean updateClub(UpdateClubDto updateClubDto){
//
//        if(updateClubDto.getProfileImage() == null){
//            Club.builder().clubName()
//        }
//
//    }







}
