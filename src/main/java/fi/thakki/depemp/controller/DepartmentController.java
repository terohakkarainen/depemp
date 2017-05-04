package fi.thakki.depemp.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import fi.thakki.depemp.dto.AddDepartmentDto;
import fi.thakki.depemp.dto.DepartmentDetailsDto;
import fi.thakki.depemp.dto.DepartmentListDto;
import fi.thakki.depemp.dto.ResponseDtoBase;
import fi.thakki.depemp.service.DepartmentService;
import fi.thakki.depemp.service.DepartmentService.DepartmentNotFoundException;

@RestController
public class DepartmentController {

	@Autowired
	private DepartmentService myDepartmentService;

	@RequestMapping(value = "/departments", method = RequestMethod.GET)
	public List<DepartmentListDto> listDepartments() {
		return myDepartmentService.listDepartments();
	}

	@RequestMapping(value = "/departments/{id}", method = RequestMethod.GET)
	public DepartmentDetailsDto getDepartment(@PathVariable Long id) throws DepartmentNotFoundException {
		return myDepartmentService.getDepartment(id);
	}

	@RequestMapping(value = "/departments", method = RequestMethod.POST)
	public ResponseEntity<ResponseDtoBase> addDepartment(
			@ModelAttribute("department") @Valid AddDepartmentDto department,
			Errors errors) {
		if (errors.hasErrors()) {
			return ResponseEntity.badRequest().body(ValidationErrorBuilder.fromBindingErrors(errors));
		}
		return ResponseEntity.ok(myDepartmentService.addDepartment(department));
	}

	@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="No department found with id")
	@ExceptionHandler(DepartmentNotFoundException.class)
	private void handleDepartmentNotFoundException() {
		// Nothing
	}
}
