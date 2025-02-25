package ru.otus.java.pro.mt.core.transfers.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Schema(description = "Запрос на исполнение перевода")
@NoArgsConstructor
@Data
@AllArgsConstructor
public class ExecuteTransferDtoRq {

    @Schema(
            description = "Идентификатор клиента-получателя",
            example = "0123456789",
            requiredMode = Schema.RequiredMode.REQUIRED,
            minLength = 10,
            maxLength = 10
    )
    private String targetClientId;

    @Schema(
            description = "Номер счёта отправителя",
            example = "012345678912",
            requiredMode = Schema.RequiredMode.REQUIRED,
            minLength = 12,
            maxLength = 12
    )
    private String sourceAccount;

    @Schema(
            description = "Номер счёта получателя",
            example = "012345678912",
            requiredMode = Schema.RequiredMode.REQUIRED,
            minLength = 12,
            maxLength = 12
    )
    private String targetAccount;

    @Schema(
            description = "Соообщение о переводе",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED,
            maxLength = 255
    )
    private String message;

    @Schema(
            description = "Сумма сделки",
            example = "1.00",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private BigDecimal amount;
}