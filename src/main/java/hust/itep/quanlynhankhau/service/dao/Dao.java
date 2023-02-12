package hust.itep.quanlynhankhau.service.dao;

import hust.itep.quanlynhankhau.service.database.EntityManagerUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;

import java.util.List;

public abstract class Dao<T> {
    public List<T> getAll(Class<T> classObject) {
        EntityManager entityManager = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(classObject);
        criteriaQuery.from(classObject);
        List<T> entities = entityManager.createQuery(criteriaQuery).getResultList();
        entityManager.getTransaction().commit();
        return entities;
    }
    public T get(Class<T> classObject, Long id) {
        EntityManager entityManager = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();
        T entity = entityManager.find(classObject, id);
        entityManager.getTransaction().commit();
        return entity;
    }
    public void save(T t) {
        EntityManager entityManager = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(t);
        entityManager.getTransaction().commit();
    }
    public void delete(T t) {
        EntityManager entityManager = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();
        T toDelete = entityManager.merge(t);
        entityManager.remove(toDelete);
        entityManager.getTransaction().commit();
    }

    public void update(T t) {
        EntityManager entityManager = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(t);
        entityManager.getTransaction().commit();
    }
}
