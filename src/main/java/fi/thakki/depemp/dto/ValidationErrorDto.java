package fi.thakki.depemp.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

public class ValidationErrorDto extends ResponseDtoBase {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<String> myErrors = new ArrayList<>();

    private final String myErrorMessage;

    public ValidationErrorDto(String errorMessage) {
        myErrorMessage = errorMessage;
    }

    public void addValidationError(String error) {
        myErrors.add(error);
    }

    public List<String> getErrors() {
        return myErrors;
    }

    public String getErrorMessage() {
        return myErrorMessage;
    }
}
