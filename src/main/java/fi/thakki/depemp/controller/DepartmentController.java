package fi.thakki.depemp.controller;

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
import fi.thakki.depemp.dto.DepartmentAddedDto;
import fi.thakki.depemp.dto.DepartmentDetailsDto;
import fi.thakki.depemp.dto.ListDepartmentsDto;
import fi.thakki.depemp.dto.ErrorResponseDto;
import fi.thakki.depemp.service.DepartmentService;
import fi.thakki.depemp.service.DepartmentService.DepartmentNotFoundException;
import fi.thakki.depemp.service.DepartmentService.DuplicateDepartmentNameException;
import fi.thakki.depemp.transformer.ErrorResponseTransformer;

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
    private ErrorResponseTransformer myErrorResponseTransformer;

    public DepartmentController(
            DepartmentService departmentService,
            ErrorResponseTransformer errorResponseTransformer) {
        myDepartmentService = departmentService;
        myErrorResponseTransformer = errorResponseTransformer;
    }

    @RequestMapping(value = "/departments", method = RequestMethod.GET)
    public ListDepartmentsDto listDepartments() {
        return myDepartmentService.listDepartments();
    }

    @RequestMapping(value = "/departments/{id}", method = RequestMethod.GET)
    public ResponseEntity<DepartmentDetailsDto> getDepartment(
            @PathVariable Long id) throws DepartmentNotFoundException {
        return ResponseEntity.ok(myDepartmentService.getDepartment(id));
    }

    @RequestMapping(value = "/departments", method = RequestMethod.POST)
    public ResponseEntity<DepartmentAddedDto> addDepartment(
            @Valid @RequestBody AddDepartmentDto department,
            Errors errors) throws DuplicateDepartmentNameException {
        if (errors.hasErrors()) {
            throw new ValidationFailedException(errors);
        }
        return ResponseEntity.ok(myDepartmentService.addDepartment(department));
    }

    @ExceptionHandler(DepartmentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponseDto handleException(
            DepartmentNotFoundException dnfe) {
        return myErrorResponseTransformer
                .toErrorResponseDto("No department found with such id");
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
        return myErrorResponseTransformer
                .toErrorResponseDto("Department with exact same name already exists");
    }
}
