package fi.thakki.depemp.service;

import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fi.thakki.depemp.dao.GenericDao;
import fi.thakki.depemp.dto.AddDepartmentDto;
import fi.thakki.depemp.dto.DepartmentAddedDto;
import fi.thakki.depemp.dto.DepartmentDetailsDto;
import fi.thakki.depemp.dto.ListDepartmentsDto;
import fi.thakki.depemp.model.Department;
import fi.thakki.depemp.transformer.DepartmentTransformer;

@Service
public class DepartmentService {

    public static class DepartmentNotFoundException extends Exception {
        // Nothing
    }

    private GenericDao myGenericDao;
    private DepartmentTransformer myTransformer;

    public DepartmentService(
            GenericDao genericDao,
            DepartmentTransformer transformer) {
        myGenericDao = genericDao;
        myTransformer = transformer;
    }

    @Transactional(readOnly = true)
    public ListDepartmentsDto listDepartments() {
        ListDepartmentsDto result = new ListDepartmentsDto();
        result.departments = myGenericDao.findAll(Department.class).stream()
                .map(d -> myTransformer.toListDto(d)).collect(Collectors.toList());
        return result;
    }

    @Transactional(readOnly = true)
    public DepartmentDetailsDto getDepartment(
            Long id) throws DepartmentNotFoundException {
        Department department = myGenericDao.find(id, Department.class);
        if (department != null) {
            return myTransformer.toDetailsDto(department);
        }
        throw new DepartmentNotFoundException();
    }

    @Transactional
    public DepartmentAddedDto addDepartment(
            AddDepartmentDto dto) {
        DepartmentAddedDto result = new DepartmentAddedDto();
        Department department = myTransformer.toDepartment(dto);
        myGenericDao.persist(department);
        result.id = department.getId();
        return result;
    }
}
