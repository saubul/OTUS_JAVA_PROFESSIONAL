package ru.otus.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ChatSessions {

    @JsonProperty("chat_sessions")
    private List<ChatSession> chatSessions;

}
