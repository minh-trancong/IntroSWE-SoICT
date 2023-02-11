package hust.itep.quanlynhankhau.dao;

import hust.itep.quanlynhankhau.model.Population;
import hust.itep.quanlynhankhau.service.dao.PopulationDao;

import java.time.LocalDate;
import java.util.ArrayList;

public class PopulationDaoTest {
    public static void main(String[] args) {
        PopulationDao populationDao = new PopulationDao();
        Population population = new Population();
        population.setAddress("123");
        population.setPassportNumber("123");
        population.setName("Khanh");
        population.setPhoneNumber("123");
        population.setGender("123");
        population.setNativePlace("123");
        population.setEthnicity("123");
        population.setBirthdate(LocalDate.now());

        ArrayList<Population> populations = new ArrayList<>(populationDao.getAll());

        for (Population population1 : populations) {
            System.out.println(population1.getName());
        }


    }
}
