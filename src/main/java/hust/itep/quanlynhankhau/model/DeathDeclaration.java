package hust.itep.quanlynhankhau.model;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Date;

@Entity
@Table(name = "death_declaration")
public class DeathDeclaration {
    private Long id;

    private Population deceased;
    private Date deathDate;

    private Date reportedAt;

    private String reason;

    private Population reporter;

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @OneToOne()
    @JoinColumn(name = "deceased_id")
    public Population getDeceased() {
        return deceased;
    }


    public void setDeceased(Population deceased) {
        this.deceased = deceased;
    }

    @Column(name = "death_date")
    public Date getDeathDate() {
        return deathDate;
    }

    public void setDeathDate(Date deathDate) {
        this.deathDate = deathDate;
    }

    @Column(name = "reported_at")
    public Date getReportedAt() {
        return reportedAt;
    }

    public void setReportedAt(Date reportedAt) {
        this.reportedAt = reportedAt;
    }

    @Column(name = "reason")
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @ManyToOne()
    @JoinColumn(name = "reporter_id")
    public Population getReporter() {
        return reporter;
    }

    public void setReporter(Population reporter) {
        this.reporter = reporter;
    }
}
