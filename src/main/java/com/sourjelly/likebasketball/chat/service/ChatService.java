package com.sourjelly.likebasketball.chat.service;

import com.sourjelly.likebasketball.chat.domain.ChatMessage;
import com.sourjelly.likebasketball.chat.domain.ChatParticipant;
import com.sourjelly.likebasketball.chat.domain.ChatRoom;
import com.sourjelly.likebasketball.chat.dto.ChatMessageDto;
import com.sourjelly.likebasketball.chat.dto.RoomInfoDto;
import com.sourjelly.likebasketball.chat.repository.ChatMessageRepository;
import com.sourjelly.likebasketball.chat.repository.ChatParticipantRepository;
import com.sourjelly.likebasketball.chat.repository.ChatRoomRepository;
import com.sourjelly.likebasketball.goods.doamin.Goods;
import com.sourjelly.likebasketball.goods.repository.GoodsRepository;
import com.sourjelly.likebasketball.user.domain.User;
import com.sourjelly.likebasketball.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatParticipantRepository chatParticipantRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final GoodsRepository goodsRepository; // 상품 정보 조회용
    private final UserRepository userRepository;   // 유저 정보 조회용

    // 1. [방 생성/조회] 상품 상세에서 버튼 눌렀을 때 실행
    @Transactional
    public long createOrGetGoodsRoom(long goodsId, long sellerId, long buyerId) {
        long fail = -1;
        return chatRoomRepository.findByRoomTypeAndTargetIdAndSenderId(ChatRoom.RoomType.GOODS, goodsId, buyerId)
                .map(ChatRoom::getId)// map형태로 {key, "value"} 가아닌 mapping한다 라는 뜻의 map
                .orElseGet(() -> {
                    // 방 생성
                    if(sellerId == buyerId){
                        return fail;
                    }
                    ChatRoom room = chatRoomRepository.save(ChatRoom.builder()
                            .roomType(ChatRoom.RoomType.GOODS)
                            .targetId(goodsId)
                            .senderId(buyerId)
                            .build());

                    // 참여자 등록 (판매자, 구매자)
                    chatParticipantRepository.save(ChatParticipant.builder().roomId(room.getId()).userId(sellerId).build());
                    chatParticipantRepository.save(ChatParticipant.builder().roomId(room.getId()).userId(buyerId).build());
                    return room.getId();
                });
    }

    // 2. [목록 조회] 채팅 메인 페이지 사이드바용
    public List<RoomInfoDto> getUserChatList(long myUserId) {
        return chatParticipantRepository.findAllByUserId(myUserId).stream()
            .map(p -> {
                ChatRoom room = chatRoomRepository.findById(p.getRoomId()).get();
                Goods goods = goodsRepository.findById(room.getTargetId()).get();

                User user = userRepository.findById(goods.getUserId()).get();
                // 상대방 찾기 (참여자 중 내가 아닌 사람)
                // 실제로는 room_id로 참여자 리스트를 뽑아 필터링해야 하지만 간단히 구현
                return RoomInfoDto.builder()
                        .roomId(room.getId())
                        .roomType(room.getRoomType())
                        .userId(user.getId())
                        .userStatus(user.getUserStatus())
                        .nickName(user.getNickName())
                        .goodsId(goods.getId())
                        .title(goods.getTitle()) // 상품 제목을 방 이름으로
                        .build();
        }).collect(Collectors.toList());
    }

    // 3. [메시지 저장] 핸들러에서 호출
    @Transactional
    public void saveMessage(ChatMessageDto dto) {
        chatMessageRepository.save(ChatMessage.builder()
                .roomId(dto.getRoomId())
                .senderId(dto.getSenderId())
                .message(dto.getMessage())
                .build());
    }
    // 채팅방의 채팅 기록 가져오기
    public List<ChatMessage> getChatMessages(long roomId) {
        return chatMessageRepository.findAllByRoomIdOrderByCreatedAtAsc(roomId);
    }
}
