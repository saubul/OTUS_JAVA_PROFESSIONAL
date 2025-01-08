package ru.otus.java.pro.spring.app.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.java.pro.spring.app.dtos.ExecuteTransferDtoRq;
import ru.otus.java.pro.spring.app.entities.Account;
import ru.otus.java.pro.spring.app.entities.Transfer;
import ru.otus.java.pro.spring.app.exceptions_handling.BusinessLogicException;
import ru.otus.java.pro.spring.app.exceptions_handling.ValidationException;
import ru.otus.java.pro.spring.app.exceptions_handling.ValidationFieldError;
import ru.otus.java.pro.spring.app.repositories.TransferRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

@Service
@RequiredArgsConstructor
public class TransferService {

    private final TransferRepository transfersRepository;

    private final AccountService accountService;

    public Optional<Transfer> getTransferById(String id, String clientId) {
        return transfersRepository.findByIdAndClientId(id, clientId);
    }

    public List<Transfer> getAllTransfers(String clientId) {
        Transfer probe = Transfer.builder()
                .clientId(clientId)
                .targetClientId(clientId)
                .build();
        return transfersRepository.findAll(Example.of(probe, ExampleMatcher.matchingAny()
                .withMatcher("clientId", exact())
                .withMatcher("targetClientId", exact())));
    }

    @Transactional
    public void execute(String clientId, ExecuteTransferDtoRq executeTransferDtoRq) {
        validateExecuteTransferDtoRq(executeTransferDtoRq);
        executeTransfer(clientId, executeTransferDtoRq);
    }

    private void executeTransfer(String clientId, ExecuteTransferDtoRq executeTransferDtoRq) {
        Account sourceAccount = accountService.findByClientIdAndNumber(clientId, executeTransferDtoRq.sourceAccount());
        if (accountService.checkAccountBlock(sourceAccount)) {
            throw new BusinessLogicException("ACCOUNT_IS_BLOCKED", "Перевод невозможен. Причина - счёт \"%s\" заблокирован".formatted(sourceAccount.getNumber()));
        }

        Account targetAccount = accountService.findByClientIdAndNumber(executeTransferDtoRq.targetClientId(), executeTransferDtoRq.targetAccount());
        if (accountService.checkAccountBlock(targetAccount)) {
            throw new BusinessLogicException("ACCOUNT_IS_BLOCKED", "Перевод невозможен. Причина - счёт \"%s\" заблокирован".formatted(targetAccount.getNumber()));
        }

        accountService.changeAccountBalance(sourceAccount, executeTransferDtoRq.amount());
        accountService.changeAccountBalance(targetAccount, executeTransferDtoRq.amount());

        Transfer transfer = Transfer.builder()
                .id(UUID.randomUUID().toString())
                .amount(executeTransferDtoRq.amount())
                .clientId(clientId)
                .sourceAccount(sourceAccount)
                .targetAccount(targetAccount)
                .targetClientId(executeTransferDtoRq.targetClientId())
                .message(executeTransferDtoRq.message())
                .build();
        transfersRepository.save(transfer);
    }

    private void validateExecuteTransferDtoRq(ExecuteTransferDtoRq executeTransferDtoRq) {
        List<ValidationFieldError> errors = new ArrayList<>();
        if (executeTransferDtoRq.sourceAccount().length() != 12) {
            errors.add(new ValidationFieldError("sourceAccountId", "Длина поля счет отправителя должна составлять 12 символов"));
        }
        if (executeTransferDtoRq.targetAccount().length() != 12) {
            errors.add(new ValidationFieldError("targetAccountId", "Длина поля счет получателя должна составлять 12 символов"));
        }
        if (executeTransferDtoRq.amount().compareTo(BigDecimal.ZERO) <= 0) {
            errors.add(new ValidationFieldError("amount", "Сумма перевода должна быть больше 0"));
        }
        if (!errors.isEmpty()) {
            throw new ValidationException("EXECUTE_TRANSFER_VALIDATION_ERROR", "Проблемы заполнения полей перевода", errors);
        }
    }
}
