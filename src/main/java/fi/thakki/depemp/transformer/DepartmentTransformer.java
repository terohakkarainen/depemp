package fi.thakki.depemp.transformer;

import org.springframework.stereotype.Service;

import fi.thakki.depemp.dto.DepartmentDto;
import fi.thakki.depemp.model.Department;

@Service
public class DepartmentTransformer {

	public DepartmentDto transform(Department department) {
		DepartmentDto result = new DepartmentDto();
		result.setName(department.getName());
		return result;
	}
}
