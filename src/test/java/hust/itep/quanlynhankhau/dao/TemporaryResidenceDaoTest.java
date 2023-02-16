package hust.itep.quanlynhankhau.dao;

import hust.itep.quanlynhankhau.model.population.TemporaryResidence;
import hust.itep.quanlynhankhau.service.dao.population.TemporaryResidenceDao;

public class TemporaryResidenceDaoTest {
    public static void main(String[] args) {
        TemporaryResidenceDao temporaryResidenceDao = new TemporaryResidenceDao();

        temporaryResidenceDao.save(new TemporaryResidence());
    }


}
