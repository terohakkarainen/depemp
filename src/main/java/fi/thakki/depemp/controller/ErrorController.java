package fi.thakki.depemp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import fi.thakki.depemp.dto.ErrorResponseDto;
import fi.thakki.depemp.transformer.ErrorResponseTransformer;

@ControllerAdvice
@RestController
public class ErrorController {

    static final String ERROR_MESSAGE_FORMAT = "Uh-oh, something strange happened: %s";

    @Autowired
    private ErrorResponseTransformer myErrorResponseTransformer;

    @ExceptionHandler(value = Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponseDto handleThrowable(
            Throwable t) {
        return myErrorResponseTransformer
                .toErrorResponseDto(String.format(ERROR_MESSAGE_FORMAT, t.getMessage()));
    }
}
