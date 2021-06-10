package ru.schernolyas.testtask.service;

import org.springframework.stereotype.Service;
import ru.schernolyas.testtask.dto.StatementInfo;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class StatementChecker {

    public void check(StatementInfo statementInfo) {
        Optional<BigDecimal> totalDebitOperationsOpt = statementInfo.getStatementRows().stream()
                .map(r -> r.getDebit()).reduce((bigDecimal, bigDecimal2) -> bigDecimal.add(bigDecimal2));
        Optional<BigDecimal> totalCreditOperationsOpt = statementInfo.getStatementRows().stream()
                .map(r -> r.getCredit()).reduce((bigDecimal, bigDecimal2) -> bigDecimal.add(bigDecimal2));
        boolean isCreditCorrect = statementInfo.getStatementSummary().getCredit().compareTo(totalCreditOperationsOpt.get()) == 0;
        boolean isDebitCorrect = statementInfo.getStatementSummary().getDebit().compareTo(totalDebitOperationsOpt.get()) == 0;

        BigDecimal calculatedFinalBalance = statementInfo.getInitialBalance().add(totalCreditOperationsOpt.get())
                .subtract(totalDebitOperationsOpt.get());
        boolean isFinalBalanceCorrect = statementInfo.getFinalBalance().compareTo(calculatedFinalBalance) == 0;
        statementInfo.setCorrect(isCreditCorrect && isDebitCorrect && isFinalBalanceCorrect);

    }


}
