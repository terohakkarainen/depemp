package fi.thakki.depemp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fi.thakki.depemp.dto.DepartmentDto;
import fi.thakki.depemp.service.DepartmentService;

@RestController
public class DepartmentController {

	@Autowired
	private DepartmentService myDepartmentService;
	
	@RequestMapping("/departments")
    public List<DepartmentDto> listDepartments() {
		return myDepartmentService.listDepartments();
    }
}
