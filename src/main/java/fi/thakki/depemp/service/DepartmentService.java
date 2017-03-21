package fi.thakki.depemp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.thakki.depemp.dao.DepartmentDao;
import fi.thakki.depemp.dto.DepartmentDto;
import fi.thakki.depemp.model.Department;
import fi.thakki.depemp.transformer.DepartmentTransformer;

@Service
public class DepartmentService {

	@Autowired
	private DepartmentDao myDepartmentDao;
	
	@Autowired
	private DepartmentTransformer myTransformer;
	
    public List<DepartmentDto> listDepartments() {
    	List<DepartmentDto> result = new ArrayList<>();
    	for(Department d : myDepartmentDao.listAll()) {
    		result.add(myTransformer.transform(d));
    	}
      	return result;
    }
}
