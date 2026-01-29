package com.sourjelly.likebasketball.chat.dto;


import com.sourjelly.likebasketball.chat.domain.ChatRoom;
import com.sourjelly.likebasketball.user.domain.UserStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(toBuilder = true)
public class RoomInfoDto {

    private long roomId;
    private ChatRoom.RoomType roomType;


    // roomType goods일때 goods 정보 넣을거
    private long goodsId;
    private String title;

    // user goods 올린 사용자 정보 or 채팅방 상대방 정보
    private long userId;
    private String nickName;
    private UserStatus userStatus;


}
