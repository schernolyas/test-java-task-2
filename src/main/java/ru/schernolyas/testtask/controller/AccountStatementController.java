package ru.schernolyas.testtask.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.schernolyas.testtask.dto.ValidationResult;

@RestController
@RequestMapping("/api/v1/registration/unacash")
public class AccountStatementController {

    @PostMapping(name = "/documentinfo", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ValidationResult uploadStatement(
            @RequestPart(name = "file", required = true) MultipartFile file) {
        return new ValidationResult(true);
    }

}
