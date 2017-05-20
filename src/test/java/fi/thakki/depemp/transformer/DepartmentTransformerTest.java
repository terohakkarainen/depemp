package fi.thakki.depemp.transformer;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import fi.thakki.depemp.Application;
import fi.thakki.depemp.dto.DepartmentListDto;
import fi.thakki.depemp.model.Department;
import fi.thakki.depemp.model.EntityFactory;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-integrationTest.properties")
public class DepartmentTransformerTest {

    @Autowired
    private DepartmentTransformer myTransformerUnderTest;

    @Autowired
    private EntityFactory myEntityFactory;

    @Test
    public void toListDtoHappyCase() {
        Department department = myEntityFactory.newDepartment("Foobar",
                "A little bit longer description");
        myEntityFactory.persist(department);

        DepartmentListDto result = myTransformerUnderTest.toListDto(department);

        assertEquals(department.getId().longValue(), result.id.longValue());
        assertEquals(department.getName(), result.name);
    }
}
