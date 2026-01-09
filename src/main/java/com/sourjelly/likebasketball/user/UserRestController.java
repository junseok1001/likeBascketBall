package com.sourjelly.likebasketball.user;


import com.sourjelly.likebasketball.common.responseApi.ResponseApi;
import com.sourjelly.likebasketball.user.domain.User;
import com.sourjelly.likebasketball.user.domain.UserStatus;
import com.sourjelly.likebasketball.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.swing.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;


@RequestMapping("/user")
@RestController
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

    // 회원정보 수정 기능
    @PutMapping("/modify/{id}")
    public ResponseEntity<ResponseApi<Void>> modify(
            @PathVariable long id
            , @RequestParam String password
            , @RequestParam String nickname
            , @RequestParam String phoneNumber){

        if(userService.changeUserInfo(id, password, nickname, phoneNumber) != null){
            return ResponseEntity.ok(ResponseApi.success("회원정보 수정 완료"));
        }else{
            // 예외 처리 추가사항
            // 1. 파라미터가 다 안들어 왔을경우 1)빈곳있다고 알려주거나 원래 값 넣어주기 bad-request 시 api에 실패 띄우기
            // 2.
            return ResponseEntity.ok(ResponseApi.fail("회원정보 수정 실패") );
        }
    }

    //회원가입 기능
    @PostMapping("/join")
    public Map<String, String> join(
            @RequestParam String loginId
            , @RequestParam String password
            , @RequestParam String nickName
            , @RequestParam UserStatus userStatus
            , @RequestParam LocalDate birthday
            , @RequestParam String phoneNumber
            , @RequestParam String email
            , @RequestParam String provider
            , HttpSession session){

        Map<String, String> resultMap = new HashMap<>();

//        ---------------------------------------------------------
//        이곳에서 데이터의 유효성 검사가 있고 넘어가야 될거 같음
        StringBuilder message = new StringBuilder();
        if(session.getAttribute("email") != null){
            if(!session.getAttribute("email").toString().equals(email)){
                message.append("카카오톡 email이 수정되었습니다. 다시확인하세요\n");
            }

            if(!session.getAttribute("nickname").toString().equals(nickName)){
                message.append("카카오에서 제공된 이름이 수정되었습니다. 다시 확인하세요\n");
            }

            if(!session.getAttribute("provider").toString().equals(provider)){
                message.append("기본제공된 정보가 수정했습니다. 다시 시도해보세요\n");
            }
                // 카카오톡 소셜 로그인
                // 카카오톡 정보로 회원가입하기
            if(message.length() == 0 ){
                if(userService.insertMember(loginId, password, nickName, userStatus, birthday, phoneNumber, email, provider)){
                    session.removeAttribute("nickname");
                    session.removeAttribute("email");
                    session.removeAttribute("provider");
                    resultMap.put("result", "success");
                }else{
                    resultMap.put("result", "fail");
                }
            }else{
                resultMap.put("message", message.toString());
            }
        }else{
            // 일반 회원 가입하기
            if(userService.insertMember(loginId, password, nickName, userStatus, birthday, phoneNumber, email, provider)){
                session.removeAttribute("nickname");
                session.removeAttribute("email");
                session.removeAttribute("provider");
                resultMap.put("result", "success");
            }else{
                resultMap.put("result", "fail");
            }
        }

        return resultMap;
    }
    // 아이디 중복 기능
    @PostMapping("/isDuplicate")
    public Map<String, String> isDuplicateId(
            @RequestParam String loginId){

        Map<String, String> resultMap = new HashMap<>();
        if(userService.isDuplicateId(loginId)){
            resultMap.put("result", "success");
        }else{
            resultMap.put("result", "fail");
        }
        return resultMap;
    }
    // 로그인 기능
    @PostMapping("/login")
    public Map<String, String> login(
            @RequestParam String loginId
            , @RequestParam String password
            , HttpSession session){

        Map<String , String> resultMap = new HashMap<>();
        User user = userService.isExistId(loginId, password);

        if(user != null){
            resultMap.put("result", "success");
            session.setAttribute("user", user);
        }else{
            resultMap.put("result", "fail");
        }
        return resultMap;
    }


}
