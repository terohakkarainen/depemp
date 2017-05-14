package fi.thakki.depemp.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import fi.thakki.depemp.model.Department;

public class AddDepartmentDto {
    
	@NotNull
	@Size(min=1, max=Department.NAME_LENGTH)
	public String name;

	@Size(max=Department.DESCRIPTION_LENGTH)
	public String description;
}
