package com.keyin.api.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "passengers")
public class Passenger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true, length = 15)
    private String phoneNumber;

    // ✅ Passenger lives in ONE city
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id", nullable = false)
    @JsonBackReference // prevents infinite recursion City -> Passenger -> City
    private City city;

    // ✅ Passenger can fly on MANY aircraft
    @ManyToMany
    @JoinTable(
            name = "passenger_aircraft",
            joinColumns = @JoinColumn(name = "passenger_id"),
            inverseJoinColumns = @JoinColumn(name = "aircraft_id")
    )
    @JsonManagedReference // matches @JsonBackReference in Aircraft
    private List<Aircraft> aircraftList = new ArrayList<>();

    // ✅ Constructors
    public Passenger() {}

    public Passenger(String firstName, String lastName, String phoneNumber, City city) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.city = city;
    }

    public Passenger(String firstName, String lastName, String phoneNumber, City city, List<Aircraft> aircraftList) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.city = city;
        this.aircraftList = aircraftList;
    }

    // ✅ Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public City getCity() { return city; }
    public void setCity(City city) { this.city = city; }

    public List<Aircraft> getAircraftList() { return aircraftList; }
    public void setAircraftList(List<Aircraft> aircraftList) { this.aircraftList = aircraftList; }
}
