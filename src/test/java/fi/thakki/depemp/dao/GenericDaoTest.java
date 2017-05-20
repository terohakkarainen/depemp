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
    
    @Test(expected = IllegalArgumentException.class)
    public void findAllForNonEntityClassThrowsException() {
        assertThat(myDaoUnderTest.findAll(Runnable.class)).isEmpty();
    }

    @Test
    public void findNonExistingIdReturnsNotFound() {
        assertThat(myDaoUnderTest.find(-100, Department.class).isPresent()).isFalse();
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void findNonEntityClassThrowsException() {
        myDaoUnderTest.find(0, Runnable.class);
    }
    
    @Test(expected = NullPointerException.class)
    public void findWithNullClassThrowsException() {
        myDaoUnderTest.find(0, null);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void persistNullEntityThrowsException() {
        myDaoUnderTest.persist(null);
    }
}
