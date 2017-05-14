package fi.thakki.depemp.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.springframework.stereotype.Repository;

import fi.thakki.depemp.model.Identifiable;

@Repository
public class GenericDao {

    private EntityManager myEntityManager;

    public GenericDao(
            EntityManager entityManager) {
        myEntityManager = entityManager;
    }

    public <T> List<T> findAll(
            Class<T> clazz) {
        CriteriaQuery<T> query = criteriaBuilder().createQuery(clazz);
        return myEntityManager.createQuery(query.select(query.from(clazz))).getResultList();
    }

    private CriteriaBuilder criteriaBuilder() {
        return myEntityManager.getCriteriaBuilder();
    }

    public <T> Optional<T> find(
            final Long id,
            final Class<T> clazz) {
        return Optional.ofNullable(myEntityManager.find(clazz, id));
    }

    public <T extends Identifiable> Long persist(
            T entity) {
        myEntityManager.persist(entity);
        return entity.getId();
    }
}
