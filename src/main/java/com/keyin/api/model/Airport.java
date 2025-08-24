package com.keyin.api.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "airports")
public class Airport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true, length = 5)
    private String code;

    // ✅ Many Airports -> One City
    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    @JsonBackReference // prevents recursion City -> Airport -> City
    private City city;

    // ✅ One Airport -> Many Aircraft
    @OneToMany(mappedBy = "airport", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // matches @JsonBackReference in Aircraft
    private List<Aircraft> aircraftList = new ArrayList<>();

    // Constructors
    public Airport() {}

    public Airport(String name, String code, City city) {
        this.name = name;
        this.code = code;
        this.city = city;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public City getCity() { return city; }
    public void setCity(City city) { this.city = city; }

    public List<Aircraft> getAircraftList() { return aircraftList; }
    public void setAircraftList(List<Aircraft> aircraftList) { this.aircraftList = aircraftList; }

    // ✅ Helper methods for syncing both sides
    public void addAircraft(Aircraft aircraft) {
        aircraftList.add(aircraft);
        aircraft.setAirport(this);
    }

    public void removeAircraft(Aircraft aircraft) {
        aircraftList.remove(aircraft);
        aircraft.setAirport(null);
    }
}
