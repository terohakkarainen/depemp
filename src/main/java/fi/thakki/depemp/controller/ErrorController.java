package fi.thakki.depemp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import fi.thakki.depemp.dto.ErrorResponseDto;
import fi.thakki.depemp.service.DepartmentService.DepartmentNotFoundException;
import fi.thakki.depemp.service.DepartmentService.DuplicateDepartmentNameException;
import fi.thakki.depemp.transformer.ErrorResponseTransformer;

@ControllerAdvice
@RestController
public class ErrorController {

    static final String ERROR_MESSAGE_FORMAT = "Uh-oh, something strange happened...";
    static final String ERROR_NO_DEPARTMENT_FOUND = "No department found with such id";
    static final String ERROR_DUPLICATE_DEPARTMENT_NAME = "Department with exact same name already exists";

    @Autowired
    private ErrorResponseTransformer myErrorResponseTransformer;

    @ExceptionHandler(DepartmentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponseDto handleException(
            DepartmentNotFoundException dnfe) {
        return myErrorResponseTransformer.toErrorResponseDto(ERROR_NO_DEPARTMENT_FOUND);
    }

    @ExceptionHandler(ValidationFailedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponseDto handleException(
            ValidationFailedException vfe) {
        return myErrorResponseTransformer.toErrorResponseDto(vfe.getErrors());
    }

    @ExceptionHandler(DuplicateDepartmentNameException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ErrorResponseDto handleException(
            DuplicateDepartmentNameException ddne) {
        return myErrorResponseTransformer.toErrorResponseDto(ERROR_DUPLICATE_DEPARTMENT_NAME);
    }

    @ExceptionHandler(value = Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponseDto handleThrowable(
            Throwable t) {
        return myErrorResponseTransformer.toErrorResponseDto(ERROR_MESSAGE_FORMAT, t.getMessage());
    }
}
