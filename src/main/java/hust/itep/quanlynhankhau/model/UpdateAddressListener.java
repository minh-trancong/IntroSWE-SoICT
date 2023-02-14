package hust.itep.quanlynhankhau.model;

import hust.itep.quanlynhankhau.service.database.EntityManagerUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PreUpdate;

import java.sql.Date;
import java.time.LocalDate;

public class UpdateAddressListener {
    @PreUpdate
    public void onUpdate(Population population) {
        EntityManagerFactory entityManagerFactory = EntityManagerUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Population old = entityManager.find(Population.class, population.getId());

        if (old.getCurrentAddress() != population.getCurrentAddress()) {
            PopulationAddressModification populationAddressModification = new PopulationAddressModification();
            populationAddressModification.setNewAddress(old.getCurrentAddress());
            populationAddressModification.setOldAddress(population.getCurrentAddress());
            populationAddressModification.setChangeDate(Date.valueOf(LocalDate.now()));
            populationAddressModification.setPopulation(population);
            entityManager.getTransaction().begin();
            entityManager.persist(populationAddressModification);
            entityManager.getTransaction().commit();
        }

    }
}
