package hust.itep.quanlynhankhau.model.covid;

import hust.itep.quanlynhankhau.model.population.Population;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Date;

@Entity
@Table(name = "quarantine_information")
public class QuarantineInformation {
    private Long id;

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private Population population;

    @ManyToOne
    @JoinColumn(name = "population_id")
    public Population getPopulation() {
        return population;
    }

    public void setPopulation(Population population) {
        this.population = population;
    }

    private String contactLevel;

    @Column(name = "contact_level")
    public String getContactLevel() {
        return contactLevel;
    }

    public void setContactLevel(String contactLevel) {
        this.contactLevel = contactLevel;
    }

    private Boolean testCovid;

    @Column(name = "test_covid")
    public Boolean getTestCovid() {
        return testCovid;
    }

    public void setTestCovid(Boolean testCovid) {
        this.testCovid = testCovid;
    }

    private Date startTime;

    @Column(name = "start_time")
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    private Date endTime;

    @Column(name = "end_time")
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    private String location;

    @Column(name = "location")
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
