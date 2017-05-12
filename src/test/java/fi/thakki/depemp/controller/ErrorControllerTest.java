package fi.thakki.depemp.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import fi.thakki.depemp.Application;
import fi.thakki.depemp.dao.GenericDao;
import fi.thakki.depemp.dto.ListDepartmentsDto;
import fi.thakki.depemp.model.Department;
import fi.thakki.depemp.model.builder.DepartmentBuilder;

@ActiveProfiles("errorControllerTest")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class ErrorControllerTest {

    @Autowired
    private GenericDao myMockedGenericDao;

    @Autowired
    private TestRestTemplate myRestTemplate;

    @Test
    public void testFoo() throws Exception {
        String departmentName = "foo";
        Department d = new DepartmentBuilder().name(departmentName).get();
        Mockito.when(myMockedGenericDao.findAll(Department.class))
                .thenReturn(Lists.newArrayList(d));

        List<Department> deps = myMockedGenericDao.findAll(Department.class);

        assertThat(deps.size()).isEqualTo(1);
        assertThat(deps.get(0).getName()).isEqualTo(departmentName);
    }
    
    @Test
    public void errorHandlerTakesIn() throws Exception {
        Mockito.when(myMockedGenericDao.findAll(Department.class))
                .thenThrow(new JpaSystemException(new RuntimeException("foo")));
       
        ResponseEntity<ListDepartmentsDto> result = myRestTemplate.getForEntity("/departments",
                ListDepartmentsDto.class);
        
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        System.out.println(result);
    }
}
