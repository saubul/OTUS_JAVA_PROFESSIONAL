package ru.otus.entities;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@JacksonXmlRootElement(localName = "BODY")
public class PhoneMessages {

    @JsonProperty("phone_messages")
    private List<PhoneMessage> phoneMessages;

}
