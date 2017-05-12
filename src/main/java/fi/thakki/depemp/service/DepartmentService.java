package fi.thakki.depemp.service;

import java.util.Optional;
import java.util.stream.Collectors;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.orm.jpa.JpaSystemException;
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
        // Nothing.
    }

    public static class DuplicateDepartmentNameException extends Exception {
        // Nothing.
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
        Optional<Department> optDepartment = myGenericDao.find(id, Department.class);
        return myTransformer
                .toDetailsDto(optDepartment.orElseThrow(DepartmentNotFoundException::new));
    }

    @Transactional(rollbackFor = DuplicateDepartmentNameException.class)
    public DepartmentAddedDto addDepartment(
            AddDepartmentDto dto) throws DuplicateDepartmentNameException {
        try {
            DepartmentAddedDto result = new DepartmentAddedDto();
            Department department = myTransformer.toDepartment(dto);
            myGenericDao.persist(department);
            result.id = department.getId();
            return result;
        } catch (JpaSystemException jse) {
            if (jse.contains(ConstraintViolationException.class)) {
                // Assume constraint violation refers to name uniqueness. If
                // other constraints are defined in the future, refactor this
                // code to be more specific.
                throw new DuplicateDepartmentNameException();
            }
            throw jse;
        }
    }
}
