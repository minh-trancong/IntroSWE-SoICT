package hust.itep.quanlynhankhau.service.dao;

import hust.itep.quanlynhankhau.database.HibernateConfig;
import hust.itep.quanlynhankhau.model.Account;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.HashMap;
import java.util.List;

public class AccountDao implements Dao<Account> {

    @Override
    public List<Account> getAll() {
        Session session = HibernateConfig.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        List<Account> accounts = session.createQuery("FROM account", Account.class).list();
        transaction.commit();
        session.close();
        return accounts;
    }

    @Override
    public Account get(Long id) {
        Session session = HibernateConfig.getSessionFactory().openSession();
        Account account = session.get(Account.class, id);
        session.close();
        return account;
    }

    @Override
    public void save(Account account) {

    }

    @Override
    public void delete(Account account) {

    }

    @Override
    public void update(Account account, HashMap<String, Object> columnMap) {

    }
}
