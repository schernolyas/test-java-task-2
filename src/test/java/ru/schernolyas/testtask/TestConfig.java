package ru.schernolyas.testtask;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import ru.schernolyas.testtask.service.StatementParser;

@TestConfiguration
@ActiveProfiles("test")
public class TestConfig {

    @Bean
    public StatementParser statementParser() {
        return new StatementParser();
    }
}
