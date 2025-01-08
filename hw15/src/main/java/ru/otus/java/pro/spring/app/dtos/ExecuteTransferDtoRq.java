package ru.otus.java.pro.spring.app.dtos;

import java.math.BigDecimal;

public record ExecuteTransferDtoRq(String targetClientId, String sourceAccount, String targetAccount, String message, BigDecimal amount) {
}