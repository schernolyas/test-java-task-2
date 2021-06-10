package ru.schernolyas.testtask.dto;

import java.math.BigDecimal;

public class StatementSummary {
    private BigDecimal debit;
    private BigDecimal credit;

    public StatementSummary(BigDecimal debit, BigDecimal credit) {
        this.debit = debit;
        this.credit = credit;
    }

    public BigDecimal getDebit() {
        return debit;
    }

    public BigDecimal getCredit() {
        return credit;
    }
}
