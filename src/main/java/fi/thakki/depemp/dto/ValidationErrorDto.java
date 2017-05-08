package fi.thakki.depemp.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

public class ValidationErrorDto {

    private String myErrorMessage;
    
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<String> myErrors = new ArrayList<>();

    public String getErrorMessage() {
        return myErrorMessage;
    }

    public void setErrorMessage(String msg) {
        myErrorMessage = msg;
    }
    
    public List<String> getErrors() {
        return myErrors;
    }

    public void setErrors(List<String> errors) {
        myErrors.addAll(errors);
    }
    
    public void addValidationError(String error) {
        myErrors.add(error);
    }
}
