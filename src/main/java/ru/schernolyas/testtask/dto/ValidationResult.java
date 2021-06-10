package ru.schernolyas.testtask.dto;

public class ValidationResult {
    private Boolean success;

    public ValidationResult() {
    }

    public ValidationResult(Boolean success) {
        this.success = success;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
