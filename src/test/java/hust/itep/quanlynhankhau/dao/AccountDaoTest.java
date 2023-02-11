package hust.itep.quanlynhankhau.dao;

import hust.itep.quanlynhankhau.database.HibernateConfig;
import hust.itep.quanlynhankhau.service.dao.AccountDao;

public class AccountDaoTest {
    public static void main(String[] args) {
        AccountDao accountDao = new AccountDao();

        System.out.println(accountDao.get(0l).getUsername());

    }
}
