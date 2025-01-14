package ru.otus.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class PhoneMessage {

    @JsonProperty("belong_number")
    private String belongNumber;

    @JsonProperty("lite_messages")
    private List<LiteMessage> liteMessageList;

}
