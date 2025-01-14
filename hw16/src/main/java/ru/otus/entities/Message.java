package ru.otus.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class Message {

    @JsonProperty("ROWID")
    private String rowId;

    private String attributedBody;

    @JsonProperty("belong_number")
    private String belongNumber;

    private String date;

    @JsonProperty("date_read")
    private String dateRead;

    private String guid;

    @JsonProperty("handle_id")
    private Long handleId;

    @JsonProperty("has_dd_results")
    private Integer hasDdResults;

    @JsonProperty("is_deleted")
    private Integer isDeleted;

    @JsonProperty("is_from_me")
    private Integer isFromMe;

    @JsonProperty("send_date")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Europe/Moscow")
    private Date sendDate;

    @JsonProperty("send_status")
    private Integer sendStatus;

    private String service;

    private String text;

}
