package ru.schernolyas.testtask.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.schernolyas.testtask.TestConfig;
import ru.schernolyas.testtask.dto.StatementInfo;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@Import(TestConfig.class)
public class StatementParserTest {
    @Autowired
    private StatementParser statementParser;

    @Test
    public void testAutowired() {
        assertNotNull(statementParser);
    }

    @Test
    public void checkParsing() throws UnsupportedEncodingException {
        InputStream io = getClass().getResourceAsStream("/account-statement.txt");

        StatementInfo statementInfo = statementParser.parse(new BufferedReader(new InputStreamReader(io, "UTF-8")));
        assertNotNull(statementInfo);
        assertNotNull(statementInfo.getInitialBalance());
        assertEquals(new BigDecimal(12583.78).setScale(2, RoundingMode.HALF_UP), statementInfo.getInitialBalance());
        assertNotNull(statementInfo.getFinalBalance());
        assertEquals(new BigDecimal(13731.78).setScale(2, RoundingMode.HALF_UP), statementInfo.getFinalBalance());
        assertNotNull(statementInfo.getStatementSummary());
        assertEquals(new BigDecimal(1250.00).setScale(2, RoundingMode.HALF_UP), statementInfo.getStatementSummary().getCredit());
        assertEquals(new BigDecimal(102.00).setScale(2, RoundingMode.HALF_UP), statementInfo.getStatementSummary().getDebit());

    }


}
