package ru.otus.bank.service.impl;

import ru.otus.bank.entity.Account;
import ru.otus.bank.entity.Agreement;
import ru.otus.bank.service.AccountService;
import ru.otus.bank.service.PaymentProcessor;
import ru.otus.bank.service.exception.AccountException;

import java.math.BigDecimal;

public class PaymentProcessorImpl implements PaymentProcessor {
    private AccountService accountService;

    public PaymentProcessorImpl(AccountService accountService) {
        this.accountService = accountService;
    }

    public boolean makeTransfer(Agreement source, Agreement destination, int sourceType,
                                int destinationType, BigDecimal amount) {

        Account sourceAccount = findAccountByAgreementAndType(source, sourceType);

        Account destinationAccount = findAccountByAgreementAndType(destination, destinationType);

        return accountService.makeTransfer(sourceAccount.getId(), destinationAccount.getId(), amount);
    }

    @Override
    public boolean makeTransferWithComission(Agreement source, Agreement destination,
                                             int sourceType, int destinationType,
                                             BigDecimal amount,
                                             BigDecimal comissionPercent) {

        Account sourceAccount = findAccountByAgreementAndType(source, sourceType);

        Account destinationAccount = findAccountByAgreementAndType(destination, destinationType);

        accountService.charge(sourceAccount.getId(), amount.negate().multiply(comissionPercent));

        return accountService.makeTransfer(sourceAccount.getId(), destinationAccount.getId(), amount);
    }

    private Account findAccountByAgreementAndType(Agreement agreement, int type) {
        return accountService.getAccounts(agreement).stream()
                .filter(account -> account.getType() == type)
                .findAny()
                .orElseThrow(() -> new AccountException("Account not found"));
    }

}
