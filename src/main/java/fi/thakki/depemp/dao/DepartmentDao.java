package fi.thakki.depemp.dao;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Repository;

import fi.thakki.depemp.model.Department;

@Repository
public class DepartmentDao {

	public List<Department> listAll() {
		Department dep1 = new Department();
		dep1.setName("Section 42");
		return Arrays.asList(dep1);
	}
}
