package ru.otus.java.pro.spring.app.mapper;

import org.springframework.stereotype.Component;
import ru.otus.java.pro.spring.app.dtos.AccountDto;
import ru.otus.java.pro.spring.app.entities.Account;

import java.math.BigDecimal;
import java.util.Objects;

@Component
public class AccountMapper implements Mapper<Account, AccountDto> {
    @Override
    public AccountDto toDto(Account account) {
        return new AccountDto(account.getId(), account.getNumber(), account.getClientId(),
                account.getBalance().toString(), Objects.equals(account.getIsBlock(), 1));
    }

    @Override
    public Account toEntity(AccountDto accountDto) {
        return new Account(accountDto.getId(), accountDto.getNumber(), accountDto.getClientId(),
                new BigDecimal(accountDto.getBalance()), accountDto.isBlock() ? 1 : 0);
    }
}
