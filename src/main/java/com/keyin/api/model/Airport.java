package com.keyin.api.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "airports")
public class Airport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true, length = 10)
    private String code;

    // One airport belongs to exactly one city
    @OneToOne
    @JoinColumn(name = "city_id", nullable = false, unique = true)
    @JsonBackReference // prevents infinite recursion with City -> Airport -> City
    private City city;

    // Constructors
    public Airport() {}

    public Airport(String name, String code, City city) {
        this.name = name;
        this.code = code;
        setCity(city); // use setter to keep sync
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public City getCity() { return city; }
    public void setCity(City city) {
        this.city = city;
        if (city != null && city.getAirport() != this) {
            city.setAirport(this); // keep both sides in sync
        }
    }
}
