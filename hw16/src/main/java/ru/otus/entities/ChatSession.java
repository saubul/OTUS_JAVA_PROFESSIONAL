package ru.otus.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ChatSession {

    @JsonProperty("chat_id")
    private Long chatId;

    @JsonProperty("chat_identifier")
    private String chatIdentifier;

    @JsonProperty("display_name")
    private String displayName;

    @JsonProperty("is_deleted")
    private Integer isDeleted;

    private List<Member> members;

    private List<Message> messages;

}
