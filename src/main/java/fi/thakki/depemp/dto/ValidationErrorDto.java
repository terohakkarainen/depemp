package fi.thakki.depemp.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

public class ValidationErrorDto extends ResponseDtoBase {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<String> myErrors = new ArrayList<>();

    private String myErrorMessage;

    public ValidationErrorDto() {
    }
    
    public ValidationErrorDto(String errorMessage) {
        myErrorMessage = errorMessage;
    }

    public ValidationErrorDto(String errorMessage, List<String> errors) {
        this(errorMessage);
        myErrors.addAll(errors);
    }
    
    public void addValidationError(String error) {
        myErrors.add(error);
    }

    public List<String> getErrors() {
        return myErrors;
    }

    public void setErrors(List<String> errors) {
        myErrors.addAll(errors);
    }
    
    public void setErrorMessage(String msg) {
        myErrorMessage = msg;
    }
    
    public String getErrorMessage() {
        return myErrorMessage;
    }
}
