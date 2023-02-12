package hust.itep.quanlynhankhau.dao;

import hust.itep.quanlynhankhau.service.dao.population.PopulationDao;

public class PopulationDaoTest {
    public static void main(String[] args) {
        PopulationDao populationDao = new PopulationDao();
        System.out.println(populationDao.getByCitizenId("123456789").getName());
    }
}
