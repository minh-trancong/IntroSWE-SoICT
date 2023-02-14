package hust.itep.quanlynhankhau.service.dao;

import hust.itep.quanlynhankhau.model.Account;
import hust.itep.quanlynhankhau.model.Population;
import hust.itep.quanlynhankhau.service.database.EntityManagerUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class AccountDao extends Dao<Account> {
    public Account getByUsername(String username) {
        EntityManager entityManager = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Account> criteria = builder.createQuery(Account.class);
        Root<Account> root = criteria.from(Account.class);
        criteria.select(root).where(builder.equal(root.get("username"), username));

        Account account = null;
        try {
            account = entityManager.createQuery(criteria).getSingleResult();
        } catch (Exception e) {
            entityManager.getTransaction().commit();
            return null;
        }

        return account ;
    }

}
