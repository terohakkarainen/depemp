package fi.thakki.depemp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fi.thakki.depemp.dao.GenericDao;
import fi.thakki.depemp.dto.DepartmentDetailsDto;
import fi.thakki.depemp.dto.DepartmentListDto;
import fi.thakki.depemp.model.Department;
import fi.thakki.depemp.transformer.DepartmentTransformer;

@Service
public class DepartmentService {

	public static class DepartmentNotFoundException extends Exception {
		// Nothing
	}
	
	@Autowired
	private GenericDao myGenericDao;
	
	@Autowired
	private DepartmentTransformer myTransformer;
	
	@Transactional(readOnly = true)
	public List<DepartmentListDto> listDepartments() {
      	List<DepartmentListDto> result = new ArrayList<>();
      	for(Department d : myGenericDao.findAll(Department.class)) {
    		result.add(myTransformer.toListDto(d));
    	}
      	return result;
    }

	@Transactional(readOnly = true)
	public DepartmentDetailsDto getDepartment(Long id) throws DepartmentNotFoundException {
		Department department = myGenericDao.find(id, Department.class);
		if(department != null) {
			return myTransformer.toDetailsDto(department);
		}
		throw new DepartmentNotFoundException();
	}
}
