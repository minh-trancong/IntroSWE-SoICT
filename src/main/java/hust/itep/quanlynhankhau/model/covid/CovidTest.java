package hust.itep.quanlynhankhau.model.covid;

import hust.itep.quanlynhankhau.model.population.Population;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Date;

@Entity
@Table(name = "covid_test")
public class CovidTest {
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


    private Date testDate;

    @Column(name = "test_date")
    public Date getTestDate() {
        return testDate;
    }

    public void setTestDate(Date testDate) {
        this.testDate = testDate;
    }

    private Boolean result;

    @Column(name = "result")
    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    private Boolean quarantine;

    @Column(name = "quarantine")
    public Boolean getQuarantine() {
        return quarantine;
    }

    public void setQuarantine(Boolean quarantine) {
        this.quarantine = quarantine;
    }

    private String location;

    @Column(name = "location")
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    private String method;

    @Column(name = "method")
    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
