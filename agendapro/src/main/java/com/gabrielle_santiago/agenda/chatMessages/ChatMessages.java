package com.gabrielle_santiago.agenda.chatMessages;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessages {
    private String username;
    private String message;

    public ChatMessages(){};

    public ChatMessages(String username, String message) {
        this.username = username;
        this.message = message;
    }
}
