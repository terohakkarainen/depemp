package fi.thakki.depemp.service;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.thakki.depemp.dao.DepartmentDao;
import fi.thakki.depemp.dto.DepartmentDetailsDto;
import fi.thakki.depemp.dto.DepartmentListDto;
import fi.thakki.depemp.model.Department;
import fi.thakki.depemp.transformer.DepartmentTransformer;

@Service
public class DepartmentService {

	public static class DepartmentNotFoundException extends ValidationException {
		// Nothing
	}
	
	@Autowired
	private DepartmentDao myDepartmentDao;
	
	@Autowired
	private DepartmentTransformer myTransformer;
	
    public List<DepartmentListDto> listDepartments() {
    	List<DepartmentListDto> result = new ArrayList<>();
    	for(Department d : myDepartmentDao.listAll()) {
    		result.add(myTransformer.toListDto(d));
    	}
      	return result;
    }

	public DepartmentDetailsDto getDepartment(Long id) throws DepartmentNotFoundException {
		Department department = myDepartmentDao.findDepartment(id);
		if(department != null) {
			return myTransformer.toDetailsDto(department);
		}
		throw new DepartmentNotFoundException();
	}
}
