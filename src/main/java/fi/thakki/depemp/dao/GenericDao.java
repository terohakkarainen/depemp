package fi.thakki.depemp.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class GenericDao {

	@Autowired
	private EntityManager myEm;

    public <T> List<T> findAll(Class<T> clazz) {
        CriteriaQuery<T> query = criteriaBuilder().createQuery(clazz);
        return myEm.createQuery(query.select(query.from(clazz))).getResultList();
    }

	public <T> T find(final Long id, final Class<T> clazz) {
		return myEm.find(clazz, id);
	}
	
	private CriteriaBuilder criteriaBuilder() {
		return myEm.getCriteriaBuilder();
	}
}
