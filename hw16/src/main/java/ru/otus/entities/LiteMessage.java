package ru.otus.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class LiteMessage {

    private String last;

    @JsonProperty("chat_identifier")
    private String chatIdentifier;

    @JsonProperty("send_date")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Europe/Moscow")
    private Date sendDate;

    private String text;

}
