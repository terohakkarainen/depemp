package fi.thakki.depemp.transformer;

import java.util.Arrays;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import fi.thakki.depemp.dto.ErrorResponseDto;

@Service
public class ErrorResponseTransformer {

    public static final String VALIDATION_ERROR_FORMAT = "Validation failed: %d error(s)";
    public static final String VALIDATION_DETAIL_FORMAT = "%s: %s";

    public ErrorResponseDto toErrorResponseDto(
            Errors errors) {
        ErrorResponseDto result = new ErrorResponseDto();
        result.errorMessage = String.format(VALIDATION_ERROR_FORMAT, errors.getErrorCount());
        errors.getFieldErrors().stream().forEach(e -> result.details
                .add(String.format(VALIDATION_DETAIL_FORMAT, e.getField(), e.getDefaultMessage())));
        return result;
    }

    public ErrorResponseDto toErrorResponseDto(
            String message) {
        ErrorResponseDto result = new ErrorResponseDto();
        result.errorMessage = message;
        return result;
    }

    public ErrorResponseDto toErrorResponseDto(
            String message,
            String... details) {
        ErrorResponseDto result = new ErrorResponseDto();
        result.errorMessage = message;
        Arrays.asList(details).stream().forEach(s -> result.details.add(s));
        return result;
    }
}
