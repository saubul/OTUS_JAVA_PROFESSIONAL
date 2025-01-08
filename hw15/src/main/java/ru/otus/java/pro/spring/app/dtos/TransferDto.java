package ru.otus.java.pro.spring.app.dtos;

import java.math.BigDecimal;

public record TransferDto(String id, String clientId, String targetClientId, String sourceAccountId,
                          String targetAccountId, String message, BigDecimal amount) {
}