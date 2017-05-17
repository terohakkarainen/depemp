package fi.thakki.depemp.model;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EntityFactory {

    @Autowired
    private EntityManager myEm;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void persist(
            Object obj) {
        myEm.persist(obj);
    }
}
