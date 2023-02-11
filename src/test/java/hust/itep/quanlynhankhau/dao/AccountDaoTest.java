package hust.itep.quanlynhankhau.dao;

import hust.itep.quanlynhankhau.model.Account;
import hust.itep.quanlynhankhau.service.dao.AccountDao;

public class AccountDaoTest {
    public static void main(String[] args) {
        AccountDao accountDao = new AccountDao();

        Account account = accountDao.get(Account.class, 4l);

        accountDao.delete(account);
    }
}
