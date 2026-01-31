package com.sourjelly.likebasketball.chat;

import com.sourjelly.likebasketball.chat.domain.ChatMessage;
import com.sourjelly.likebasketball.chat.dto.RoomInfoDto;
import com.sourjelly.likebasketball.chat.service.ChatService;
import com.sourjelly.likebasketball.user.domain.User;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatRestController {

    private final ChatService chatService;

    @PostMapping("/room/goods")
    public long getRoom(@RequestParam long goodsId, @RequestParam long sellerId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        return chatService.createOrGetGoodsRoom(goodsId, sellerId, user.getId());
    }

    @GetMapping("/messages/{roomId}")
    public List<ChatMessage> getChatHistory(@PathVariable long roomId) {
        // 최근 50개만 가져오거나 하는 처리가 나중에 필요하겠지만, 일단 전체 조회
        return chatService.getChatMessages(roomId);
    }
}
