package fi.thakki.depemp.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

public class ErrorResponseDto {

    public String errorMessage;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<String> details = new ArrayList<>();
}
