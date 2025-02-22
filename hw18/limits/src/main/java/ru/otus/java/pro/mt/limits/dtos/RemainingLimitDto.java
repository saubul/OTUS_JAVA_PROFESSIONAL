package ru.otus.java.pro.mt.limits.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RemainingLimitDto {
    private BigDecimal remainingLimit;
}
