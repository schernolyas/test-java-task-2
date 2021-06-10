package ru.schernolyas.testtask.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.schernolyas.testtask.TestConfig;
import ru.schernolyas.testtask.dto.StatementInfo;
import ru.schernolyas.testtask.util.BigDecimalUtil;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

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
        assertEquals(BigDecimalUtil.build(12583.78), statementInfo.getInitialBalance());
        assertNotNull(statementInfo.getFinalBalance());
        assertEquals(BigDecimalUtil.build(13731.78), statementInfo.getFinalBalance());
        assertNotNull(statementInfo.getStatementSummary());
        assertEquals(BigDecimalUtil.build(1250.00), statementInfo.getStatementSummary().getCredit());
        assertEquals(BigDecimalUtil.build(102.00), statementInfo.getStatementSummary().getDebit());
    }
}
