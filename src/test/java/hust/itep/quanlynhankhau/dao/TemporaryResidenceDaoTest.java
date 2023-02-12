package hust.itep.quanlynhankhau.dao;

import hust.itep.quanlynhankhau.model.TemporaryResidence;
import hust.itep.quanlynhankhau.service.dao.TemporaryResidenceDao;

public class TemporaryResidenceDaoTest {
    public static void main(String[] args) {
        TemporaryResidenceDao temporaryResidenceDao = new TemporaryResidenceDao();

        temporaryResidenceDao.save(new TemporaryResidence());
    }


}
