package ru.schernolyas.testtask.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class StatementInfo {
    private BigDecimal initialBalance;
    private BigDecimal finalBalance;
    private List<StatementRow> statementRows = new ArrayList<>();
    private StatementSummary statementSummary;
    private Boolean correct;
}
