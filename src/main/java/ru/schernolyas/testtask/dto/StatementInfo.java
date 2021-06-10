package ru.schernolyas.testtask.dto;

import java.math.BigDecimal;
import java.util.List;

public class StatementInfo {
    private BigDecimal initialBalance;
    private BigDecimal finalBalance;
    private List<StatementRow> statementRows;
    private StatementSummary statementSummary;
    private Boolean correct;

    public StatementInfo() {
    }

    public BigDecimal getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(BigDecimal initialBalance) {
        this.initialBalance = initialBalance;
    }

    public BigDecimal getFinalBalance() {
        return finalBalance;
    }

    public void setFinalBalance(BigDecimal finalBalance) {
        this.finalBalance = finalBalance;
    }

    public List<StatementRow> getStatementRows() {
        return statementRows;
    }

    public void setStatementRows(List<StatementRow> statementRows) {
        this.statementRows = statementRows;
    }

    public StatementSummary getStatementSummary() {
        return statementSummary;
    }

    public void setStatementSummary(StatementSummary statementSummary) {
        this.statementSummary = statementSummary;
    }

    public Boolean getCorrect() {
        return correct;
    }

    public void setCorrect(Boolean correct) {
        this.correct = correct;
    }


}
