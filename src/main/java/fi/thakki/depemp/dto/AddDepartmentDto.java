package fi.thakki.depemp.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import fi.thakki.depemp.model.Department;

public class AddDepartmentDto {

	@NotNull
	@Size(min=1, max=Department.NAME_LENGTH)
	private String name;

	@Size(max=Department.DESCRIPTION_LENGTH)
	private String description;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
