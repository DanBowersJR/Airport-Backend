package com.keyin.api.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Aircraft {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;
    private String airlineName;
    private int numberOfPassengers;

    // Aircraft can carry many passengers
    @ManyToMany(mappedBy = "aircraftList")
    private List<Passenger> passengers;

    // Aircraft can use many airports (takeoff/landing)
    @ManyToMany
    @JoinTable(
            name = "aircraft_airport",
            joinColumns = @JoinColumn(name = "aircraft_id"),
            inverseJoinColumns = @JoinColumn(name = "airport_id")
    )
    private List<Airport> airports;

    // Constructors
    public Aircraft() {}

    public Aircraft(String type, String airlineName, int numberOfPassengers) {
        this.type = type;
        this.airlineName = airlineName;
        this.numberOfPassengers = numberOfPassengers;
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

    public List<Airport> getAirports() { return airports; }

    public void setAirports(List<Airport> airports) { this.airports = airports; }
}
