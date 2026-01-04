package com.sourjelly.likebasketball.user.service;


import com.sourjelly.likebasketball.user.domain.User;
import com.sourjelly.likebasketball.user.domain.UserStatus;
import com.sourjelly.likebasketball.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class UserService {


    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public boolean insertMember(
            String loginId
            , String password
            , String nickName
            , UserStatus userStatus
            , LocalDate birthday
            , String phoneNumber
            , String email
    ){
        String encodingPassword = passwordEncoder.encode(password);

        User user = User.builder()
                .loginId(loginId)
                .password(encodingPassword)
                .userStatus(userStatus)
                .nickName(nickName)
                .birthday(birthday)
                .phoneNumber(phoneNumber)
                .email(email)
                .build();

        try{
            userRepository.save(user);
        }catch(DataAccessException e){
            return false;
        }

        return true;
    }

    public boolean isDuplicateId(String loginId){

        return !userRepository.existsByLoginId(loginId);
    }

    public User isExistId(
            String loginId
            , String password){

        User user = userRepository.findByLoginId(loginId);
        String encodingPassword = user.getPassword();

        if(user != null && passwordEncoder.matches(password, encodingPassword)){
            return user;
        }

        return null;
    }

}
