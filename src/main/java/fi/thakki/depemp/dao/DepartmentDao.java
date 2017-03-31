package fi.thakki.depemp.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fi.thakki.depemp.model.Department;

@Repository
public class DepartmentDao {

	@Autowired
	private EntityManager myEm;

	public List<Department> listAll() {
		TypedQuery<Department> query = myEm.createQuery("from Department", Department.class);
		return query.getResultList();
	}

	public Department findDepartment(Long id) {
		return myEm.find(Department.class, id);
	}
}
