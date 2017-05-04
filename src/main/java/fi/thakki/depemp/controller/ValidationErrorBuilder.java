package fi.thakki.depemp.controller;

import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import fi.thakki.depemp.dto.ValidationErrorDto;

public class ValidationErrorBuilder {
	
	public static ValidationErrorDto fromBindingErrors(Errors errors) {
		ValidationErrorDto result = new ValidationErrorDto(
				String.format("Validation failed: %d error(s)", errors.getErrorCount()));
		for (FieldError error : errors.getFieldErrors()) {
			result.addValidationError(
					String.format("%s: %s", error.getField(), error.getDefaultMessage()));
		}
		return result;
	}
}
