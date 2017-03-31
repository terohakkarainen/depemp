package fi.thakki.depemp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import fi.thakki.depemp.dto.DepartmentDetailsDto;
import fi.thakki.depemp.dto.DepartmentListDto;
import fi.thakki.depemp.service.DepartmentService;
import fi.thakki.depemp.service.DepartmentService.DepartmentNotFoundException;

@RestController
public class DepartmentController {

	@Autowired
	private DepartmentService myDepartmentService;

	@RequestMapping("/departments")
	public List<DepartmentListDto> listDepartments() {
		return myDepartmentService.listDepartments();
	}

	@RequestMapping("/department/{id}")
	public DepartmentDetailsDto getDepartment(@PathVariable Long id) throws DepartmentNotFoundException {
		return myDepartmentService.getDepartment(id);
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(DepartmentNotFoundException.class)
	private void handleDepartmentNotFoundException() {
		// Nothing to do
	}
}
