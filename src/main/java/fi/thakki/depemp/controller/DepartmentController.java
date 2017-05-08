package fi.thakki.depemp.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import fi.thakki.depemp.dto.AddDepartmentDto;
import fi.thakki.depemp.dto.DepartmentDetailsDto;
import fi.thakki.depemp.dto.DepartmentListDto;
import fi.thakki.depemp.dto.ResponseDtoBase;
import fi.thakki.depemp.dto.ValidationErrorDto;
import fi.thakki.depemp.service.DepartmentService;
import fi.thakki.depemp.service.DepartmentService.DepartmentNotFoundException;
import fi.thakki.depemp.transformer.ValidationErrorTransformer;

@RestController
public class DepartmentController {

    private static class ValidationFailedException extends RuntimeException {

        private Errors myErrors;

        public ValidationFailedException(
                Errors errors) {
            myErrors = errors;
        }

        public Errors getErrors() {
            return myErrors;
        }
    }

    private DepartmentService myDepartmentService;
    private ValidationErrorTransformer myValidationErrorTransformer;

    public DepartmentController(
            DepartmentService departmentService,
            ValidationErrorTransformer validationErrorTransformer) {
        myDepartmentService = departmentService;
        myValidationErrorTransformer = validationErrorTransformer;
    }

    @RequestMapping(value = "/departments", method = RequestMethod.GET)
    public List<DepartmentListDto> listDepartments() {
        return myDepartmentService.listDepartments();
    }

    @RequestMapping(value = "/departments/{id}", method = RequestMethod.GET)
    public ResponseEntity<DepartmentDetailsDto> getDepartment(
            @PathVariable Long id) {
        // TODO: exception handling externally.
        try {
            return ResponseEntity.ok(myDepartmentService.getDepartment(id));
        } catch (DepartmentNotFoundException dnfe) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/departments", method = RequestMethod.POST)
    public ResponseEntity<? extends ResponseDtoBase> addDepartment(
            @Valid @RequestBody AddDepartmentDto department,
            Errors errors) {
        if (errors.hasErrors()) {
            throw new ValidationFailedException(errors);
        }
        return ResponseEntity.ok(myDepartmentService.addDepartment(department));
    }

    @ExceptionHandler(ValidationFailedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrorDto handle(
            ValidationFailedException vfe) {
        return myValidationErrorTransformer.toValidationErrorDto(vfe.getErrors());
    }
}
