package ru.schernolyas.testtask.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.schernolyas.testtask.TestConfig;
import ru.schernolyas.testtask.dto.StatementInfo;
import ru.schernolyas.testtask.dto.StatementRow;
import ru.schernolyas.testtask.dto.StatementSummary;
import ru.schernolyas.testtask.util.BigDecimalUtil;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@Import(TestConfig.class)
public class StatementCheckerTest {
    @Autowired
    private StatementChecker statementChecker;

    @Test
    public void testAutowired() {
        assertNotNull(statementChecker);
    }

    @Test
    public void correctStatement() {
        StatementInfo statementInfo = new StatementInfo();
        statementInfo.setCorrect(false);
        statementInfo.setInitialBalance(BigDecimalUtil.build(12583.78));
        statementInfo.setFinalBalance(BigDecimalUtil.build(13731.78));
        statementInfo.setStatementSummary(new StatementSummary(BigDecimalUtil.build(102.00), BigDecimalUtil.build(1250.00)));
        statementInfo.getStatementRows().add(new StatementRow(LocalDate.of(2021, 5, 10), "",
                BigDecimalUtil.build(100.00), BigDecimal.ZERO, ""));
        statementInfo.getStatementRows().add(new StatementRow(LocalDate.of(2021, 5, 11), "",
                BigDecimal.ZERO, BigDecimalUtil.build(1250.00), ""));
        statementInfo.getStatementRows().add(new StatementRow(LocalDate.of(2021, 5, 22), "",
                BigDecimalUtil.build(2.00), BigDecimal.ZERO, ""));
        statementChecker.check(statementInfo);
        assertTrue(statementInfo.getCorrect());
    }

    @Test
    public void incorrectDebit() {
        StatementInfo statementInfo = new StatementInfo();
        statementInfo.setCorrect(false);
        statementInfo.setInitialBalance(BigDecimalUtil.build(12583.78));
        statementInfo.setFinalBalance(BigDecimalUtil.build(13731.78));
        statementInfo.setStatementSummary(new StatementSummary(BigDecimalUtil.build(102.00), BigDecimalUtil.build(1250.00)));
        statementInfo.getStatementRows().add(new StatementRow(LocalDate.of(2021, 5, 10), "",
                BigDecimalUtil.build(101.00), BigDecimal.ZERO, ""));
        statementInfo.getStatementRows().add(new StatementRow(LocalDate.of(2021, 5, 11), "",
                BigDecimal.ZERO, BigDecimalUtil.build(1250.00), ""));
        statementInfo.getStatementRows().add(new StatementRow(LocalDate.of(2021, 5, 22), "",
                BigDecimalUtil.build(2.00), BigDecimal.ZERO, ""));
        statementChecker.check(statementInfo);
        assertFalse(statementInfo.getCorrect());
    }

    @Test
    public void incorrectCredit() {
        StatementInfo statementInfo = new StatementInfo();
        statementInfo.setCorrect(false);
        statementInfo.setInitialBalance(BigDecimalUtil.build(12583.78));
        statementInfo.setFinalBalance(BigDecimalUtil.build(13731.78));
        statementInfo.setStatementSummary(new StatementSummary(BigDecimalUtil.build(102.00), BigDecimalUtil.build(1250.00)));
        statementInfo.getStatementRows().add(new StatementRow(LocalDate.of(2021, 5, 10), "",
                BigDecimalUtil.build(100.00), BigDecimal.ZERO, ""));
        statementInfo.getStatementRows().add(new StatementRow(LocalDate.of(2021, 5, 11), "",
                BigDecimal.ZERO, BigDecimalUtil.build(2250.00), ""));
        statementInfo.getStatementRows().add(new StatementRow(LocalDate.of(2021, 5, 22), "",
                BigDecimalUtil.build(2.00), BigDecimal.ZERO, ""));
        statementChecker.check(statementInfo);
        assertFalse(statementInfo.getCorrect());
    }
}
