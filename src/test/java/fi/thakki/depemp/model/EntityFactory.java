package fi.thakki.depemp.model;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EntityFactory {

    @Autowired
    private EntityManager myEntityManager;

    @Transactional(propagation = Propagation.REQUIRED)
    public void persist(
            Object obj) {
        myEntityManager.persist(obj);
    }

    public Department newDepartment(
            String name) {
        Department result = new Department();
        result.setName(name);
        return result;
    }

    public Department newDepartment(
            String name,
            String description) {
        Department result = newDepartment(name);
        result.setDescription(description);
        return result;
    }
}
