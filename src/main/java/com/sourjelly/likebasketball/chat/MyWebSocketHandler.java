package com.sourjelly.likebasketball.chat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sourjelly.likebasketball.chat.dto.ChatMessageDto;
import com.sourjelly.likebasketball.chat.service.ChatService;
import com.sourjelly.likebasketball.common.global.CustomException;
import com.sourjelly.likebasketball.common.global.ErrorCode;
import com.sourjelly.likebasketball.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
@Slf4j
public class MyWebSocketHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper;


    // WebSocketSession을 담을 공간 -> 현재 테스트로 그냥 session만 담지만 나중에 dto에 session담아서 정보를 담을 공간
    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();


    private final ChatService chatService;


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
                sessions.put(session.getId(), session);
                log.info("사용자 접속완료 - ID : {}, 현재 접속자 수: {}", userId, sessions.size());
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
//        // [A] JSON 문자열을 DTO로 변환
//        ChatMessageDto chatMsg = objectMapper.readValue(message.getPayload(), ChatMessageDto.class);
//
//        // 보낸 데이터가 enter라면 roomId를 webSocketSession에 저장
//        if(chatMsg.getType() == ChatMessageDto.MessageType.ENTER){
//            session.getAttributes().put("roomId", chatMsg.getRoomId());
//        }
//
//        // [B] 세션에서 보낸 사람 ID 추출 (보안을 위해 클라이언트 데이터를 믿지 않고 세션값 사용)
//        long senderId = (Long) session.getAttributes().get("userId");
//        chatMsg.setSenderId(senderId);
//
////        // [C] DB 저장 서비스 호출
////        chatService.saveMessage(chatMsg);
//
//        // [D] 같은 방에 있는 사람들에게만 브로드캐스트
//        for (WebSocketSession s : sessions.values()) {
//            if(!s.isOpen()){
//                sessions.remove(s.getId());
//                continue;
//            }
//
//            // 해당 세션이 가진 방 번호 정보를 확인 (실제론 방 참여 정보 기반으로 필터링)
//            Long targetRoomId = (Long) s.getAttributes().get("roomId");
//
//            //webSocket은 객체는 생성되어있지만 연결이 끊긴 상태일수도 있어서 s.isOpen으로 확인한다.
//            if (chatMsg.getRoomId().equals(targetRoomId) && s.isOpen()) {
//                // DTO를 다시 JSON으로 변환해서 전송
//                try {
//                    s.sendMessage(new TextMessage(objectMapper.writeValueAsString(chatMsg)));
//                }
//                catch (IOException e){
//                    throw new CustomException(ErrorCode.MESSAGE_FAIL);
//                }
//            }
//        }

        ChatMessageDto chatMsg = objectMapper.readValue(message.getPayload(), ChatMessageDto.class);

        if(chatMsg.getType() == ChatMessageDto.MessageType.ENTER){
            session.getAttributes().put("roomId", chatMsg.getRoomId());
            log.info("방 입장: RoomID={}, UserID={}", chatMsg.getRoomId(), session.getAttributes().get("userId"));
            return; // 입장 처리는 여기서 끝
        }

        // [추가] DB에 저장 (Service 호출)
        long senderId = (long) session.getAttributes().get("userId");
        chatMsg.setSenderId(senderId);
        chatService.saveMessage(chatMsg); // 이 한 줄이 핵심!

        // 기존의 broadcast 호출
        broadcast(chatMsg);
    }

    // 3. 연결 종료 시
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        Long userId = (Long) session.getAttributes().get("userId");
        Long roomId = (Long) session.getAttributes().get("roomId");

        // 1. 메모리 누수 방지를 위해 반드시 맵에서 제거
        sessions.remove(session.getId());

        // 2. (선택 사항) "OO님이 접속을 종료했습니다"라고 알리고 싶다면?
        if (roomId != null) {
            ChatMessageDto leaveNotice = new ChatMessageDto();
            leaveNotice.setType(ChatMessageDto.MessageType.LEAVE);
            leaveNotice.setRoomId(roomId);
            leaveNotice.setSenderId(userId);
            leaveNotice.setMessage(userId + "님의 연결이 끊겼습니다.");
            broadcast(leaveNotice);
        }

        log.info("사용자 {} 연결 종료. 현재 접속자 수: {}", userId, sessions.size());
    }


    private void broadcast(ChatMessageDto chatMsg) throws IOException {
        // 1. DTO를 JSON 문자열로 변환 (배달할 물건 포장)
        String jsonPayload = objectMapper.writeValueAsString(chatMsg);

        // 2. 현재 서버에 연결된 모든 세션(사람)을 하나씩 확인
        for (WebSocketSession s : sessions.values()) {
            // 3. 각 세션이 "어느 방에 들어가 있는지" 확인 (입장 시 저장해둔 값)
            Long sessionRoomId = (Long) s.getAttributes().get("roomId");

            // 메시지의 방 번호와 세션에 저장된 방 번호가 일치하는 경우에만 전송
            if (sessionRoomId != null && chatMsg.getRoomId().equals(sessionRoomId) && s.isOpen()) {
                s.sendMessage(new TextMessage(jsonPayload));
                log.debug("실시간 메시지 전송: TargetRoom={}, TargetUser={}", sessionRoomId, s.getAttributes().get("userId"));
            }
        }
    }

}


