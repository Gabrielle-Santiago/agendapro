package com.gabrielle_santiago.agenda.chatMessages;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class ChatMessageController {

    @MessageMapping("/chat.register")
    @SendTo("/topic/public")
    public ChatMessages register(@Payload ChatMessages chatMessages, SimpMessageHeaderAccessor headerAccessor){
        headerAccessor.getSessionAttributes().put("username", chatMessages.getUsername());
        return chatMessages;
    }

    @MessageMapping("/chat.send")
    @SendTo("/topic/public")
    public ChatMessages sendMessage(@Payload ChatMessages chatMessages){
        return chatMessages;
    }
}
