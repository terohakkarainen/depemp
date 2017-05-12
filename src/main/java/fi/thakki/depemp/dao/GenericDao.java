package fi.thakki.depemp.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.springframework.stereotype.Repository;

@Repository
public class GenericDao {

    private EntityManager myEm;

    public GenericDao(
            EntityManager entityManager) {
        myEm = entityManager;
    }

    public <T> List<T> findAll(
            Class<T> clazz) {
        CriteriaQuery<T> query = criteriaBuilder().createQuery(clazz);
        return myEm.createQuery(query.select(query.from(clazz))).getResultList();
    }

    public <T> Optional<T> find(
            final Long id,
            final Class<T> clazz) {
        return Optional.ofNullable(myEm.find(clazz, id));
    }

    private CriteriaBuilder criteriaBuilder() {
        return myEm.getCriteriaBuilder();
    }

    public void persist(
            Object obj) {
        myEm.persist(obj);
    }
}
