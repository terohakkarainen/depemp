package fi.thakki.depemp.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.commons.lang.RandomStringUtils;
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
import fi.thakki.depemp.dto.ListDepartmentsDto;
import fi.thakki.depemp.dto.ValidationErrorDto;
import fi.thakki.depemp.model.Department;
import fi.thakki.depemp.model.EntityFactory;
import fi.thakki.depemp.model.builder.DepartmentBuilder;

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
        String name = randomString(Department.NAME_LENGTH);
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
        String name = randomString(Department.NAME_LENGTH);
        String desc = randomString(Department.DESCRIPTION_LENGTH);
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
        ResponseEntity<String> result = myRestTemplate.getForEntity("/departments/-100",
                String.class);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void addNewDepartment() throws Exception {
        String name = randomString(Department.NAME_LENGTH);
        String desc = randomString(Department.DESCRIPTION_LENGTH);

        AddDepartmentDto addDto = new AddDepartmentDto();
        addDto.setName(name);
        addDto.setDescription(desc);

        ResponseEntity<DepartmentAddedDto> result = myRestTemplate.postForEntity("/departments",
                addDto, DepartmentAddedDto.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        final long newDepartmentId = result.getBody().id;

        assertions(new Runnable() {
            @Override
            public void run() {
                Department department = myGenericDao.find(newDepartmentId, Department.class);
                assertThat(department.getName()).isEqualTo(name);
                assertThat(department.getDescription()).isEqualTo(desc);
            }
        });
    }

    @Test
    public void addNewDepartmentFailsOnMissingName() throws Exception {
        AddDepartmentDto addDtoWithoutName = new AddDepartmentDto();

        ResponseEntity<ValidationErrorDto> result = myRestTemplate.postForEntity("/departments",
                addDtoWithoutName, ValidationErrorDto.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        ValidationErrorDto error = result.getBody();
        assertThat(error.getErrorMessage()).isEqualTo("Validation failed: 1 error(s)");
        assertThat(error.getErrors()).contains("name: may not be null");
    }

    @Test
    public void addNewDepartmentFailsOnTooLongName() throws Exception {
        AddDepartmentDto addDto = new AddDepartmentDto();
        addDto.setName(randomString(Department.NAME_LENGTH + 1));

        ResponseEntity<ValidationErrorDto> result = myRestTemplate.postForEntity("/departments",
                addDto, ValidationErrorDto.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        ValidationErrorDto error = result.getBody();
        assertThat(error.getErrorMessage()).isEqualTo("Validation failed: 1 error(s)");
        assertThat(error.getErrors()).contains(
                String.format("name: size must be between 1 and %d", Department.NAME_LENGTH));
    }

    @Test
    public void addNewDepartmentFailsOnTooLongDescription() throws Exception {
        AddDepartmentDto addDto = new AddDepartmentDto();
        addDto.setName(randomString());
        addDto.setDescription(randomString(Department.DESCRIPTION_LENGTH + 1));

        ResponseEntity<ValidationErrorDto> result = myRestTemplate.postForEntity("/departments",
                addDto, ValidationErrorDto.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        ValidationErrorDto error = result.getBody();
        assertThat(error.getErrorMessage()).isEqualTo("Validation failed: 1 error(s)");
        assertThat(error.getErrors()).contains(String.format(
                "description: size must be between 0 and %d", Department.DESCRIPTION_LENGTH));
    }

    @Test
    public void addNewDepartmentFailsOnDuplicateName() throws Exception {
        AddDepartmentDto addDto = new AddDepartmentDto();
        addDto.setName(randomString(Department.NAME_LENGTH));

        ResponseEntity<DepartmentAddedDto> firstResult = myRestTemplate
                .postForEntity("/departments", addDto, DepartmentAddedDto.class);
        assertThat(firstResult.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<DepartmentAddedDto> secondResult = myRestTemplate
                .postForEntity("/departments", addDto, DepartmentAddedDto.class);
        assertThat(secondResult.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    private static final String randomString() {
        return randomString(5);
    }

    private static final String randomString(
            int length) {
        return RandomStringUtils.randomAlphanumeric(length);
    }
}
