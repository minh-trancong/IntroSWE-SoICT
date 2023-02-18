package hust.itep.quanlynhankhau.model.covid;


import hust.itep.quanlynhankhau.model.population.Population;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Date;

@Entity
@Table(name = "movement_declaration")
public class MovementDeclaration {
    private Long id;

    private Integer numberOfPassengers;
    private String vehicleType;
    private String licensePlate;
    private String departureLocation;
    private Date departureDate;
    private String destination;
    private String history;
    private String symptoms;
    private Boolean hasCovid;
    private Population population;

    private Date declarationDate;

    @Column(name = "declaration_date")
    public Date getDeclarationDate() {
        return declarationDate;
    }

    public void setDeclarationDate(Date declarationDate) {
        this.declarationDate = declarationDate;
    }

    @Column(name = "number_of_passengers")
    public Integer getNumberOfPassengers() {
        return numberOfPassengers;
    }

    public void setNumberOfPassengers(Integer numberOfPassengers) {
        this.numberOfPassengers = numberOfPassengers;
    }

    @Column(name = "vehicle_type")
    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    @Column(name = "license_plate")
    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    @Column(name = "departure_location")
    public String getDepartureLocation() {
        return departureLocation;
    }

    public void setDepartureLocation(String departureLocation) {
        this.departureLocation = departureLocation;
    }

    @Column(name = "departure_date")
    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    @Column(name = "destination")
    public String getDestination() {
        return destination;
    }


    public void setDestination(String destination) {
        this.destination = destination;
    }

    @Column(name = "history")
    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    @Column(name = "symptoms")
    public String getSymptoms() {
        return symptoms;
    }


    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    @Column(name = "has_covid")
    public Boolean getHasCovid() {
        return hasCovid;
    }

    public void setHasCovid(Boolean hasCovid) {
        this.hasCovid = hasCovid;
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
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
