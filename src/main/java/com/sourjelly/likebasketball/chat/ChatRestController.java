package com.sourjelly.likebasketball.chat;

import com.sourjelly.likebasketball.chat.domain.ChatMessage;
import com.sourjelly.likebasketball.chat.dto.RoomInfoDto;
import com.sourjelly.likebasketball.chat.service.ChatService;
import com.sourjelly.likebasketball.common.global.CustomException;
import com.sourjelly.likebasketball.common.global.ErrorCode;
import com.sourjelly.likebasketball.common.responseApi.ResponseApi;
import com.sourjelly.likebasketball.user.domain.User;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatRestController {

    private final ChatService chatService;

    // 메세지 방 생성 하기
    @PostMapping("/room/goods")
    public ResponseEntity<ResponseApi> getRoom(@RequestParam long goodsId, @RequestParam long sellerId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if(chatService.createOrGetGoodsRoom(goodsId, sellerId, user.getId()) == -1){
            throw new CustomException(ErrorCode.CREATE_FAIL);
        }
        return ResponseEntity.ok(ResponseApi.success("채팅방 생성성공"));
    }

    // 메세지 기록 가져오기
    @GetMapping("/messages/{roomId}")
    public List<ChatMessage> getChatHistory(@PathVariable long roomId) {
        // 최근 50개만 가져오거나 하는 처리가 나중에 필요하겠지만, 일단 전체 조회
        return chatService.getChatMessages(roomId);
    }

}
