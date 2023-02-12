package hust.itep.quanlynhankhau.service.dao.population;

import hust.itep.quanlynhankhau.model.Population;
import hust.itep.quanlynhankhau.service.dao.Dao;
import hust.itep.quanlynhankhau.service.database.EntityManagerUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class PopulationDao extends Dao<Population> {
    public Population getByCitizenId(String citizenId) {
        EntityManager entityManager = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Population> criteria = builder.createQuery(Population.class);
        Root<Population> root = criteria.from(Population.class);
        criteria.select(root).where(builder.equal(root.get("citizenId"), citizenId));

        Population population = null;
        try {
            population = entityManager.createQuery(criteria).getSingleResult();
        } catch (Exception e) {
            entityManager.getTransaction().commit();
            return null;
        }

        return population;
    }
}
