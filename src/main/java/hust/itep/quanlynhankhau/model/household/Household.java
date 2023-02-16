package hust.itep.quanlynhankhau.model.household;

import hust.itep.quanlynhankhau.model.Account;
import hust.itep.quanlynhankhau.model.population.Population;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "household")
public class Household {
    private Long householdId;
    private Population headOfHouseHold;
    private String areaCode;
    private String address;
    private Date createdAt;
    private Date relocatedAt;
    private String relocationReason;
    private Account account;

    private List<Population> populationList;

    @OneToMany(mappedBy = "household", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public List<Population> getPopulationList() {
        return populationList;
    }

    public void setPopulationList(List<Population> populationList) {
        this.populationList = populationList;
    }

    @ManyToOne
    @JoinColumn(name = "executor_id")
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @OneToOne
    @JoinColumn(name = "head_of_household_id")
    public Population getHeadOfHouseHold() {
        return headOfHouseHold;
    }

    public void setHeadOfHouseHold(Population headOfHouseHold) {
        this.headOfHouseHold = headOfHouseHold;
    }

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    public Long getHouseholdId() {
        return householdId;
    }

    public void setHouseholdId(Long householdId) {
        this.householdId = householdId;
    }
    @Column(name = "area_code")
    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "created_at")
    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Column(name = "relocated_at")
    public Date getRelocatedAt() {
        return relocatedAt;
    }

    public void setRelocatedAt(Date relocatedAt) {
        this.relocatedAt = relocatedAt;
    }
    @Column(name = "relocation_reason")
    public String getRelocationReason() {
        return relocationReason;
    }

    public void setRelocationReason(String relocationReason) {
        this.relocationReason = relocationReason;
    }


}
