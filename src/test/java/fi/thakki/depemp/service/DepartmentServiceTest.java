package fi.thakki.depemp.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class DepartmentServiceTest {

    @Autowired
    private DepartmentService myServiceUnderTest;
    
    @Test(expected = NullPointerException.class)
    public void addDepartmentHandlesNullArgument() throws Exception {
        myServiceUnderTest.addDepartment(null);
    }
}
