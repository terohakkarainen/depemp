package fi.thakki.depemp.transformer;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import fi.thakki.depemp.dto.ErrorResponseDto;

@Service
public class ErrorResponseTransformer {

    public static final String VALIDATION_ERROR_FORMAT = "Validation failed: %d error(s)";
    public static final String VALIDATION_DETAIL_FORMAT = "%s: %s";

    public ErrorResponseDto toErrorResponseDto(
            Errors errors) {
        ErrorResponseDto result = new ErrorResponseDto();
        result.setErrorMessage(
                String.format(VALIDATION_ERROR_FORMAT, errors.getErrorCount()));
        for (FieldError error : errors.getFieldErrors()) {
            result.addDetail(String.format(VALIDATION_DETAIL_FORMAT, error.getField(), error.getDefaultMessage()));
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
