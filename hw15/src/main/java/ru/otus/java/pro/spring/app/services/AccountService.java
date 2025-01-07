package ru.otus.java.pro.spring.app.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.java.pro.spring.app.dtos.AccountDto;
import ru.otus.java.pro.spring.app.entities.Account;
import ru.otus.java.pro.spring.app.exceptions_handling.BusinessLogicException;
import ru.otus.java.pro.spring.app.exceptions_handling.ResourceNotFoundException;
import ru.otus.java.pro.spring.app.mapper.AccountMapper;
import ru.otus.java.pro.spring.app.repositories.AccountRepository;

import java.math.BigDecimal;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    private final AccountMapper accountMapper;

    public AccountDto findAccountById(String id) {
        return accountMapper.toDto(this.findById(id));
    }

    @Transactional
    public void changeAccountBalance(String id, BigDecimal amount) {
        this.changeAccountBalance(this.findById(id), amount);
    }

    @Transactional
    public void changeAccountBalance(Account account, BigDecimal amount) {
        if (account.getBalance().compareTo(amount) < 0) {
            throw new BusinessLogicException("NOT_ENOUGH_ACCOUNT_BALANCE",
                    "На балансе счёта \"%s\" недостаточно средств для перевода".formatted(account.getId()));
        }
        account.setBalance(account.getBalance().subtract(amount));
    }

    public Account findById(String id) {
        return accountRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Счёт с идентификатором \"%s\" не найден".formatted(id)));
    }

    public Account findByClientIdAndNumber(String clientId, String number) {
        return accountRepository.findByClientIdAndNumber(clientId, number).orElseThrow(() ->
                new ResourceNotFoundException("Счёт с номером \"%s\" не найден у клиента \"%s\"".formatted(number, clientId)));
    }

    public boolean checkAccountBlock(Account account) {
        return Objects.equals(account.getIsBlock(), 1);
    }
}
