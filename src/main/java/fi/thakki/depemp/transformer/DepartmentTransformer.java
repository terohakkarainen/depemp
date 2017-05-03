package fi.thakki.depemp.transformer;

import org.springframework.stereotype.Service;

import fi.thakki.depemp.dto.AddDepartmentDto;
import fi.thakki.depemp.dto.DepartmentDetailsDto;
import fi.thakki.depemp.dto.DepartmentListDto;
import fi.thakki.depemp.model.Department;

@Service
public class DepartmentTransformer {

	public DepartmentListDto toListDto(Department department) {
		DepartmentListDto result = new DepartmentListDto();
		result.id = department.getId();
		result.name = department.getName();
		return result;
	}
	
	public DepartmentDetailsDto toDetailsDto(Department department) {
		DepartmentDetailsDto result = new DepartmentDetailsDto();
		result.id = department.getId();
		result.name = department.getName();
		result.description = department.getDescription();
		return result;
	}
	
	public Department toDepartment(AddDepartmentDto dto) {
		Department result = new Department();
		result.setName(dto.getName());
		result.setDescription(dto.getDescription());
		return result;
	}
}
