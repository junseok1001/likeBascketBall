package com.sourjelly.likebasketball.config;

import com.sourjelly.likebasketball.chat.MyWebSocketHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;


@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

    private final MyWebSocketHandler myWebSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(myWebSocketHandler, "/ws/chat")
                // HttpSession의 정보를 WebSocketSession으로 복사해주는 인터셉터
                .addInterceptors(new HttpSessionHandshakeInterceptor())
                // 나중에 서버 배포시 내 주소로 바꿔줘야함(도메인)
                .setAllowedOrigins("*");
    }
}
