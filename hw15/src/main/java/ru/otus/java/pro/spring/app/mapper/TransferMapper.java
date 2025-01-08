package ru.otus.java.pro.spring.app.mapper;

import org.springframework.stereotype.Component;
import ru.otus.java.pro.spring.app.dtos.TransferDto;
import ru.otus.java.pro.spring.app.entities.Transfer;

@Component
public class TransferMapper implements Mapper<Transfer, TransferDto> {

    @Override
    public TransferDto toDto(Transfer t) {
        return new TransferDto(t.getId(), t.getClientId(), t.getTargetClientId(), t.getSourceAccount().getNumber(),
                t.getTargetAccount().getNumber(), t.getMessage(), t.getAmount());
    }

    @Override
    public Transfer toEntity(TransferDto transferDto) {
        return new Transfer(transferDto.id(), transferDto.clientId(), transferDto.targetClientId(),
                null, null, transferDto.message(), transferDto.amount());
    }
}
