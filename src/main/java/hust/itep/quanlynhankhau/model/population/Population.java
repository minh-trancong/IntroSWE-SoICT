package hust.itep.quanlynhankhau.model.population;

import hust.itep.quanlynhankhau.model.Household;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Date;
import java.util.List;


@Entity
@Table(name = "population")
@EntityListeners(UpdateAddressListener.class)
public class Population {
    private Long id;
    private String name;
    private String gender;
    private Date birthdate;
    private String phone;
    private String nationality;
    private String ethnicity;
    private String citizenId;
    private String passport;
    private String birthPlace;
    private String nativePlace;
    private String occupation;
    private String permanentAddress;
    private String currentAddress;
    private Household household;
    private String relationshipToHead;

    private List<PopulationAddressModification> populationAddressModificationsList;

    @OneToMany(mappedBy = "population", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public List<PopulationAddressModification> getPopulationAddressModificationsList() {
        return populationAddressModificationsList;
    }

    public void setPopulationAddressModificationsList(List<PopulationAddressModification> populationAddressModificationsList) {
        this.populationAddressModificationsList = populationAddressModificationsList;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "household_id", nullable = true)
    public Household getHousehold() {
        return household;
    }
    public void setHousehold(Household household) {
        this.household = household;
    }

    @Column(name = "relationship_to_head")
    public String getRelationshipToHead() {
        return relationshipToHead;
    }

    public void setRelationshipToHead(String relationshipToHead) {
        this.relationshipToHead = relationshipToHead;
    }

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "gender")
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Column(name = "birthdate")
    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(name = "nationality")
    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    @Column(name = "ethnicity")
    public String getEthnicity() {
        return ethnicity;
    }

    public void setEthnicity(String ethnicity) {
        this.ethnicity = ethnicity;
    }

    @Column(name = "citizen_id", unique = true)
    public String getCitizenId() {
        return citizenId;
    }

    public void setCitizenId(String citizenId) {
        this.citizenId = citizenId;
    }

    @Column(name = "passport")
    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    @Column(name = "birth_place")
    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    @Column(name = "native_place")
    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }


    @Column(name = "occupation")
    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }


    @Column(name = "permanent_address")
    public String getPermanentAddress() {
        return permanentAddress;
    }

    public void setPermanentAddress(String permanentAddress) {
        this.permanentAddress = permanentAddress;
    }

    @Column(name = "current_address")
    public String getCurrentAddress() {
        return currentAddress;
    }

    public void setCurrentAddress(String currentAddress) {
        this.currentAddress = currentAddress;
    }
}
