package hust.itep.quanlynhankhau.model;


import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Date;

@Entity
@Table(name = "temporary_residence")
public class TemporaryResidence {
    private Long id;
    private Date fromDate;
    private Date toDate;
    private String reason;
    private Population population;
    private String paperCode;

    @Column(name = "paper_code")
    public String getPaperCode() {
        return paperCode;
    }

    public void setPaperCode(String paperCode) {
        this.paperCode = paperCode;
    }

    @ManyToOne
    @JoinColumn(name = "population_id")
    public Population getPopulation() {
        return population;
    }

    public void setPopulation(Population population) {
        this.population = population;
    }

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy="increment")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "from_date")
    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    @Column(name = "to_date")
    public Date getToDate() {
        return toDate;
    }


    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    @Column(name = "reason")
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
