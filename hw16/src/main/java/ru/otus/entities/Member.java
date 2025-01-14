package ru.otus.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Member {

    private String first;

    @JsonProperty("handle_id")
    private Long handleId;

    @JsonProperty("image_path")
    private String imagePath;

    private String last;

    private String middle;

    @JsonProperty("phone_number")
    private String phoneNumber;

    private String service;

    @JsonProperty("thumb_path")
    private String thumbPath;

}
