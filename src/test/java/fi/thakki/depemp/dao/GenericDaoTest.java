package fi.thakki.depemp.dao;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import fi.thakki.depemp.model.Department;

@RunWith(SpringRunner.class)
@DataJpaTest
public class GenericDaoTest {

    @TestConfiguration
    @ComponentScan("fi.thakki.depemp.dao")
    @EntityScan("fi.thakki.depemp.model")
    public static class MyConfiguration {
        // Nothing.
    }
    
    @Autowired
    private GenericDao myDaoUnderTest;
    
    @Test
    public void findAllReturnsNothingWhenDatabaseIsEmpty() {
        assertThat(myDaoUnderTest.findAll(Department.class)).isEmpty();
    }
}
