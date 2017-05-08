package fi.thakki.depemp.transformer;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import fi.thakki.depemp.dto.ValidationErrorDto;

@Service
public class ValidationErrorTransformer {

    public ValidationErrorDto toValidationErrorDto(
            Errors errors) {
        ValidationErrorDto result = new ValidationErrorDto();
        result.setErrorMessage(
                String.format("Validation failed: %d error(s)", errors.getErrorCount()));
        for (FieldError error : errors.getFieldErrors()) {
            result.addValidationError(
                    String.format("%s: %s", error.getField(), error.getDefaultMessage()));
        }
        return result;
    }
}
