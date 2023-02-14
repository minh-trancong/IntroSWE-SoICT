package hust.itep.quanlynhankhau.model;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Date;

@Entity
@Table(name = "population_address_modification")
public class PopulationAddressModification {
    private Long id;
    private String oldAddress;
    private String newAddress;
    private Date changeDate;

    private Population population;

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @Column(name = "old_address")
    public String getOldAddress() {
        return oldAddress;
    }


    public void setOldAddress(String oldAddress) {
        this.oldAddress = oldAddress;
    }

    @Column(name = "new_address")
    public String getNewAddress() {
        return newAddress;
    }

    public void setNewAddress(String newAddress) {
        this.newAddress = newAddress;
    }

    @Column(name = "changed_date")
    public Date getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "population_id")
    public Population getPopulation() {
        return population;
    }

    public void setPopulation(Population population) {
        this.population = population;
    }
}
