package ru.schernolyas.testtask.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class StatementRow {
    private LocalDate date;
    private String correspondentAccount;
    private BigDecimal debit;
    private BigDecimal credit;
    private String comment;

    public StatementRow(LocalDate date, String correspondentAccount, BigDecimal debit, BigDecimal credit, String comment) {
        this.date = date;
        this.correspondentAccount = correspondentAccount;
        this.debit = debit;
        this.credit = credit;
        this.comment = comment;
    }


}
