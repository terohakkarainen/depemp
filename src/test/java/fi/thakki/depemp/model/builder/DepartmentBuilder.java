package fi.thakki.depemp.model.builder;

import fi.thakki.depemp.model.Department;

public class DepartmentBuilder {

	private Department myDepartment = new Department();

	public DepartmentBuilder id(final Long id) {
		myDepartment.setId(id);
		return this;
	}

	public DepartmentBuilder name(final String name) {
		myDepartment.setName(name);
		return this;
	}

	public DepartmentBuilder description(final String description) {
		myDepartment.setDescription(description);
		return this;
	}

	public Department get() {
		return myDepartment;
	}
}
