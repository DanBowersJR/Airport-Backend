package com.keyin.api.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cities")
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String state;

    private int population;

    // ✅ One City -> Many Airports
    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // matches @JsonBackReference in Airport
    private List<Airport> airports = new ArrayList<>();

    // Constructors
    public City() {}

    public City(String name, String state, int population) {
        this.name = name;
        this.state = state;
        this.population = population;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public int getPopulation() { return population; }
    public void setPopulation(int population) { this.population = population; }

    public List<Airport> getAirports() { return airports; }
    public void setAirports(List<Airport> airports) { this.airports = airports; }

    // Helper method to sync both sides
    public void addAirport(Airport airport) {
        airports.add(airport);
        airport.setCity(this);
    }

    public void removeAirport(Airport airport) {
        airports.remove(airport);
        airport.setCity(null);
    }
}
