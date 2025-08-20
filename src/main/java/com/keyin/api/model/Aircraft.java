package com.keyin.api.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "aircraft")
public class Aircraft {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String airlineName;

    @Column(nullable = false)
    private int numberOfPassengers;

    // Each aircraft has 10 passengers (Many-to-Many with Passenger)
    @ManyToMany(mappedBy = "aircraftList", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonManagedReference // works with @JsonBackReference in Passenger
    private List<Passenger> passengers = new ArrayList<>();

    // Each aircraft belongs to exactly ONE airport (Many-to-One)
    @ManyToOne
    @JoinColumn(name = "airport_id", nullable = false)
    @JsonBackReference // prevents infinite recursion with Airport -> Aircraft -> Airport
    private Airport airport;

    // Constructors
    public Aircraft() {}

    public Aircraft(String type, String airlineName, int numberOfPassengers, Airport airport) {
        this.type = type;
        this.airlineName = airlineName;
        this.numberOfPassengers = numberOfPassengers;
        this.airport = airport;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getAirlineName() { return airlineName; }
    public void setAirlineName(String airlineName) { this.airlineName = airlineName; }

    public int getNumberOfPassengers() { return numberOfPassengers; }
    public void setNumberOfPassengers(int numberOfPassengers) { this.numberOfPassengers = numberOfPassengers; }

    public List<Passenger> getPassengers() { return passengers; }
    public void setPassengers(List<Passenger> passengers) { this.passengers = passengers; }

    public Airport getAirport() { return airport; }
    public void setAirport(Airport airport) { this.airport = airport; }
}
