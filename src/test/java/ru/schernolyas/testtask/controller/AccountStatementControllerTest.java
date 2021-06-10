package ru.schernolyas.testtask.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import ru.schernolyas.testtask.dto.ValidationResult;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class AccountStatementControllerTest {
    @LocalServerPort
    private Integer port = 0;
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void upload() {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<String, Object>();
        ContentDisposition contentDisposition = ContentDisposition
                .builder("form-data")
                .name("file")
                .filename("file.txt")
                .build();
        httpHeaders.setContentDisposition(contentDisposition);
        parameters.add("file", createFileResource());

        HttpEntity entity = new HttpEntity(parameters, httpHeaders);

        ResponseEntity<ValidationResult> responseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/api/v1/statement",
                HttpMethod.POST,
                entity,
                ValidationResult.class
        );
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(true, responseEntity.getBody().getSuccess());

    }

    private Resource createFileResource() {
        return new ClassPathResource("/account-statement.txt");
    }
}
