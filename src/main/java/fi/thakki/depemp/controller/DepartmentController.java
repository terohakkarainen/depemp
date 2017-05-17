package fi.thakki.depemp.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fi.thakki.depemp.dto.AddDepartmentDto;
import fi.thakki.depemp.dto.DepartmentAddedDto;
import fi.thakki.depemp.dto.DepartmentDetailsDto;
import fi.thakki.depemp.dto.ListDepartmentsDto;
import fi.thakki.depemp.service.DepartmentService;
import fi.thakki.depemp.service.DepartmentService.DepartmentNotFoundException;
import fi.thakki.depemp.service.DepartmentService.DuplicateDepartmentNameException;

@RestController
public class DepartmentController {

    private DepartmentService myDepartmentService;

    public DepartmentController(
            DepartmentService departmentService) {
        myDepartmentService = departmentService;
    }

    @GetMapping("/departments")
    public ResponseEntity<ListDepartmentsDto> listDepartments() {
        return ResponseEntity.ok(myDepartmentService.listDepartments());
    }

    @GetMapping("/departments/{id}")
    public ResponseEntity<DepartmentDetailsDto> getDepartment(
            @PathVariable Long id) throws DepartmentNotFoundException {
        return ResponseEntity.ok(myDepartmentService.getDepartment(id));
    }

    @PostMapping("/departments")
    public ResponseEntity<DepartmentAddedDto> addDepartment(
            @Valid @RequestBody AddDepartmentDto department,
            Errors errors) throws DuplicateDepartmentNameException {
        if (errors.hasErrors()) {
            throw new ValidationFailedException(errors);
        }
        return new ResponseEntity<DepartmentAddedDto>(myDepartmentService.addDepartment(department),
                HttpStatus.CREATED);
    }
}
