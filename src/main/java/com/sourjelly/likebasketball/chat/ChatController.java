package com.sourjelly.likebasketball.chat;

import com.sourjelly.likebasketball.chat.domain.ChatMessage;
import com.sourjelly.likebasketball.chat.service.ChatService;
import com.sourjelly.likebasketball.user.domain.User;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @GetMapping("/chat")
    public String chatPage(
            @RequestParam(required = false) Long roomId
            , HttpSession session
            , Model model) {


        User user = (User) session.getAttribute("user");
        model.addAttribute("chatList", chatService.getUserChatList(user.getId()));
        model.addAttribute("selectedRoomId", roomId); // 자동으로 열 방 번호
        return "chat/chat";
    }



}
