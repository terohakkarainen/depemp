package fi.thakki.depemp.transformer;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import fi.thakki.depemp.dto.ErrorResponseDto;

@Service
public class ErrorResponseTransformer {

    public ErrorResponseDto toErrorResponseDto(
            Errors errors) {
        ErrorResponseDto result = new ErrorResponseDto();
        result.setErrorMessage(
                String.format("Validation failed: %d error(s)", errors.getErrorCount()));
        for (FieldError error : errors.getFieldErrors()) {
            result.addDetail(String.format("%s: %s", error.getField(), error.getDefaultMessage()));
        }
        return result;
    }

    public ErrorResponseDto toErrorResponseDto(
            String message) {
        ErrorResponseDto result = new ErrorResponseDto();
        result.setErrorMessage(message);
        return result;
    }
}
