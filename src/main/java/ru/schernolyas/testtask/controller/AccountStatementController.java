package ru.schernolyas.testtask.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.schernolyas.testtask.dto.StatementInfo;
import ru.schernolyas.testtask.dto.ValidationResult;
import ru.schernolyas.testtask.service.StatementChecker;
import ru.schernolyas.testtask.service.StatementParser;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/statement")
@Slf4j
public class AccountStatementController {
    @Autowired
    private StatementParser statementParser;
    @Autowired
    private StatementChecker statementChecker;
    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ValidationResult uploadStatement(
            @RequestPart(name = "file", required = true) MultipartFile file) throws IOException {

        StatementInfo statementInfo = statementParser.parse(file.getInputStream());
        statementChecker.check(statementInfo);
        log.info(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(statementInfo));
        return new ValidationResult(statementInfo.getCorrect());
    }

}
