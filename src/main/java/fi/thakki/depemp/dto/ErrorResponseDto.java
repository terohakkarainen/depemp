package fi.thakki.depemp.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

public class ErrorResponseDto {

    private String myErrorMessage;
    
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<String> myDetails = new ArrayList<>();

    public String getErrorMessage() {
        return myErrorMessage;
    }

    public void setErrorMessage(String msg) {
        myErrorMessage = msg;
    }
    
    public List<String> getDetails() {
        return myDetails;
    }

    public void setDetails(List<String> details) {
        myDetails.addAll(details);
    }
    
    public void addDetail(String details) {
        myDetails.add(details);
    }
}
