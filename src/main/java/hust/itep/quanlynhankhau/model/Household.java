package hust.itep.quanlynhankhau.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Date;

public class Household {
    private Long householdId;
    private Long headOfHouseHoldId;
    private String areaCode;
    private String address;
    private Date createdAt;
    private Date relocatedAt;
    private String relocationReason;
    private Long executorID;

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    public Long getHouseholdId() {
        return householdId;
    }

    public void setHouseholdId(Long householdId) {
        this.householdId = householdId;
    }

    public Long getHeadOfHouseHoldId() {
        return headOfHouseHoldId;
    }

    public void setHeadOfHouseHoldId(Long headOfHouseHoldId) {
        this.headOfHouseHoldId = headOfHouseHoldId;
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

    public Long getExecutorID() {
        return executorID;
    }

    public void setExecutorID(Long executorID) {
        this.executorID = executorID;
    }
}
