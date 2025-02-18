package com.keyin.api.airport;

import com.keyin.api.model.City;
import jakarta.persistence.*;

@Entity
public class Airport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String code;  // Added missing 'code' field

    @OneToOne
    @JoinColumn(name = "city_id", referencedColumnName = "id")
    private City city;

    // Default constructor
    public Airport() {}

    // Parameterized constructor
    public Airport(String name, String code, City city) {
        this.name = name;
        this.code = code;
        this.city = city;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public City getCity() { return city; }
    public void setCity(City city) { this.city = city; }
}
