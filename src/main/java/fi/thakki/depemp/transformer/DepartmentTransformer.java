package fi.thakki.depemp.transformer;

import org.springframework.stereotype.Service;

import fi.thakki.depemp.command.AddDepartmentCommand;
import fi.thakki.depemp.dto.DepartmentDetailsDto;
import fi.thakki.depemp.dto.DepartmentListDto;
import fi.thakki.depemp.model.Department;
import fi.thakki.depemp.type.DepartmentId;

@Service
public class DepartmentTransformer {

    public DepartmentListDto toListDto(
            Department department) {
        DepartmentListDto result = new DepartmentListDto();
        result.id = DepartmentId.valueOf(department.getId());
        result.name = department.getName();
        return result;
    }

    public DepartmentDetailsDto toDetailsDto(
            Department department) {
        DepartmentDetailsDto result = new DepartmentDetailsDto();
        result.id = DepartmentId.valueOf(department.getId());
        result.name = department.getName();
        result.description = department.getDescription();
        return result;
    }

    public Department toDepartment(
            AddDepartmentCommand command) {
        Department result = new Department();
        result.setName(command.name);
        result.setDescription(command.description);
        return result;
    }
}
