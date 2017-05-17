package fi.thakki.depemp.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import fi.thakki.depemp.Application;
import fi.thakki.depemp.dto.AddDepartmentDto;
import fi.thakki.depemp.dto.DepartmentAddedDto;
import fi.thakki.depemp.dto.DepartmentDetailsDto;
import fi.thakki.depemp.dto.ErrorResponseDto;
import fi.thakki.depemp.dto.ListDepartmentsDto;
import fi.thakki.depemp.model.Department;
import fi.thakki.depemp.model.EntityFactory;
import fi.thakki.depemp.model.builder.DepartmentBuilder;
import fi.thakki.depemp.transformer.ErrorResponseTransformer;
import fi.thakki.depemp.util.StringUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-integrationTest.properties")
public class DepartmentControllerTest extends TransactionSupportingTestBase {

    @Autowired
    private TestRestTemplate myRestTemplate;

    @Autowired
    private EntityFactory myEntityFactory;

    @Test
    public void getAllDepartments() throws Exception {
        String name = StringUtil.randomString(Department.NAME_LENGTH);
        Department department = new DepartmentBuilder().name(name).get();
        myEntityFactory.persist(department);

        ResponseEntity<ListDepartmentsDto> result = myRestTemplate.getForEntity("/departments",
                ListDepartmentsDto.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        ListDepartmentsDto departmentList = result.getBody();
        assertThat(departmentList.departments.size()).isEqualTo(1);
        assertThat(departmentList.departments.get(0).name).isEqualTo(name);
    }

    @Test
    public void getExistingDepartment() throws Exception {
        String name = StringUtil.randomString(Department.NAME_LENGTH);
        String desc = StringUtil.randomString(Department.DESCRIPTION_LENGTH);
        Department department = new DepartmentBuilder().name(name).description(desc).get();
        myEntityFactory.persist(department);

        ResponseEntity<DepartmentDetailsDto> result = myRestTemplate
                .getForEntity("/departments/" + department.getId(), DepartmentDetailsDto.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        DepartmentDetailsDto details = result.getBody();
        assertThat(details.id).isGreaterThan(0);
        assertThat(details.name).isEqualTo(name);
        assertThat(details.description).isEqualTo(desc);
    }

    @Test
    public void getNonExistingDepartment() throws Exception {
        ResponseEntity<ErrorResponseDto> result = myRestTemplate.getForEntity("/departments/-100",
                ErrorResponseDto.class);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(result.getBody().errorMessage)
                .isEqualTo(ErrorController.ERROR_NO_DEPARTMENT_FOUND);
    }

    @Test
    public void addNewDepartment() throws Exception {
        String name = StringUtil.randomString(Department.NAME_LENGTH);
        String desc = StringUtil.randomString(Department.DESCRIPTION_LENGTH);

        AddDepartmentDto addDto = new AddDepartmentDto();
        addDto.name = name;
        addDto.description = desc;

        ResponseEntity<DepartmentAddedDto> result = myRestTemplate.postForEntity("/departments",
                addDto, DepartmentAddedDto.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        final long newDepartmentId = result.getBody().id;

        assertions(new Runnable() {
            @Override
            public void run() {
                Optional<Department> optDepartment = myGenericDao.find(newDepartmentId,
                        Department.class);
                assertThat(optDepartment.isPresent()).isTrue();
                assertThat(optDepartment.get().getName()).isEqualTo(name);
                assertThat(optDepartment.get().getDescription()).isEqualTo(desc);
            }
        });
    }

    @Test
    public void addNewDepartmentFailsOnMissingName() throws Exception {
        AddDepartmentDto addDtoWithoutName = new AddDepartmentDto();

        ResponseEntity<ErrorResponseDto> result = myRestTemplate.postForEntity("/departments",
                addDtoWithoutName, ErrorResponseDto.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        ErrorResponseDto error = result.getBody();
        assertValidationErrorCount(error, 1);
        assertValidationDetail(error, "name", "may not be null");
    }

    @Test
    public void addNewDepartmentFailsOnTooLongName() throws Exception {
        AddDepartmentDto addDto = new AddDepartmentDto();
        addDto.name = StringUtil.randomString(Department.NAME_LENGTH + 1);

        ResponseEntity<ErrorResponseDto> result = myRestTemplate.postForEntity("/departments",
                addDto, ErrorResponseDto.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        ErrorResponseDto error = result.getBody();
        assertValidationErrorCount(error, 1);
        assertValidationDetail(error, "name",
                String.format("size must be between 1 and %d", Department.NAME_LENGTH));
    }

    @Test
    public void addNewDepartmentFailsOnTooLongDescription() throws Exception {
        AddDepartmentDto addDto = new AddDepartmentDto();
        addDto.name = StringUtil.randomString();
        addDto.description = StringUtil.randomString(Department.DESCRIPTION_LENGTH + 1);

        ResponseEntity<ErrorResponseDto> result = myRestTemplate.postForEntity("/departments",
                addDto, ErrorResponseDto.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        ErrorResponseDto error = result.getBody();
        assertValidationErrorCount(error, 1);
        assertValidationDetail(error, "description",
                String.format("size must be between 0 and %d", Department.DESCRIPTION_LENGTH));
    }

    @Test
    public void addNewDepartmentFailsOnDuplicateName() throws Exception {
        AddDepartmentDto addDto = new AddDepartmentDto();
        addDto.name = StringUtil.randomString(Department.NAME_LENGTH);

        ResponseEntity<DepartmentAddedDto> succeedingAdd = myRestTemplate
                .postForEntity("/departments", addDto, DepartmentAddedDto.class);
        assertThat(succeedingAdd.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        ResponseEntity<ErrorResponseDto> failingAdd = myRestTemplate.postForEntity("/departments",
                addDto, ErrorResponseDto.class);
        assertThat(failingAdd.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(failingAdd.getBody().errorMessage)
                .isEqualTo(ErrorController.ERROR_DUPLICATE_DEPARTMENT_NAME);
    }

    @Test
    public void addNewDepartmentFailsOnTooLongNameAndDescription() throws Exception {
        AddDepartmentDto addDto = new AddDepartmentDto();
        addDto.name = StringUtil.randomString(Department.NAME_LENGTH + 1);
        addDto.description = StringUtil.randomString(Department.DESCRIPTION_LENGTH + 1);

        ResponseEntity<ErrorResponseDto> result = myRestTemplate.postForEntity("/departments",
                addDto, ErrorResponseDto.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        ErrorResponseDto error = result.getBody();

        assertValidationErrorCount(error, 2);
        assertValidationDetail(error, "name",
                String.format("size must be between 1 and %d", Department.NAME_LENGTH));
        assertValidationDetail(error, "description",
                String.format("size must be between 0 and %d", Department.DESCRIPTION_LENGTH));
    }

    private static void assertValidationErrorCount(
            ErrorResponseDto errorDto,
            int expectedCount) {
        assertThat(errorDto.errorMessage).isEqualTo(
                String.format(ErrorResponseTransformer.VALIDATION_ERROR_FORMAT, expectedCount));
    }

    private static void assertValidationDetail(
            ErrorResponseDto errorDto,
            String fieldName,
            String expectedDetail) {
        assertThat(errorDto.details).contains(String.format(
                ErrorResponseTransformer.VALIDATION_DETAIL_FORMAT, fieldName, expectedDetail));
    }
}
