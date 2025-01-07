package ru.otus.java.pro.spring.app.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {

    private String id;

    private String number;

    private String clientId;

    private String balance;

    private boolean isBlock;

}
