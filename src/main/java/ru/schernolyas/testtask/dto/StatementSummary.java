package ru.schernolyas.testtask.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class StatementSummary {
    private BigDecimal debit;
    private BigDecimal credit;

    public StatementSummary(BigDecimal debit, BigDecimal credit) {
        this.debit = debit;
        this.credit = credit;
    }


}
