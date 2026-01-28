package com.sourjelly.likebasketball.chat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sourjelly.likebasketball.chat.dto.ChatMessageDto;
import com.sourjelly.likebasketball.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
@Slf4j
public class MyWebSocketHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper;


    // WebSocketSession을 담을 공간 -> 현재 테스트로 그냥 session만 담지만 나중에 dto에 session담아서 정보를 담을 공간
    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();


    // TextWebSocketHandler는 기준 : websocket연결 관련 -> 사용자의 의도에 의해서 끊길때도 있지만 비 정상적인 끊김도 있어서
    // throw를 기반으로 하고 있다.

    // 1. 연결 성공 시
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 인터셉터로 가져온 HttpSession의 정보를 WebSocketSession 속성에서 꺼냄
        Map<String, Object> webSocketSession = session.getAttributes();

        Object userObj = session.getAttributes().get("user");

        if(userObj instanceof User){
            User user = (User) userObj;
            long userId = user.getId();

            webSocketSession.put("userId", userId);
            log.info("사용자 접속완료 - ID : {}", userId);
        }else{
            log.warn("비정상적인 접속이 되었습니다.");
            session.close(CloseStatus.POLICY_VIOLATION);
        }
//        String userId = (String) session.getAttributes().get("userId");
//        sessions.put(session.getId(), session);
//        System.out.println("연결됨: " + userId + " (Session: " + session.getId() + ")");
    }

    // 2. 메시지 수신 시 (핵심)
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // [A] JSON 문자열을 DTO로 변환
        ChatMessageDto chatMsg = objectMapper.readValue(message.getPayload(), ChatMessageDto.class);

        // [B] 세션에서 보낸 사람 ID 추출 (보안을 위해 클라이언트 데이터를 믿지 않고 세션값 사용)
        long senderId = (Long) session.getAttributes().get("userId");
        chatMsg.setSenderId(senderId);

//        // [C] DB 저장 서비스 호출
//        chatService.saveMessage(chatMsg);

        // [D] 같은 방에 있는 사람들에게만 브로드캐스트
        for (WebSocketSession s : sessions.values()) {
            // 해당 세션이 가진 방 번호 정보를 확인 (실제론 방 참여 정보 기반으로 필터링)
            Long targetRoomId = (Long) s.getAttributes().get("roomId");

            if (chatMsg.getRoomId().equals(targetRoomId) && s.isOpen()) {
                // DTO를 다시 JSON으로 변환해서 전송
                s.sendMessage(new TextMessage(objectMapper.writeValueAsString(chatMsg)));
            }
        }
    }

    // 3. 연결 종료 시
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session.getId());
    }




}
