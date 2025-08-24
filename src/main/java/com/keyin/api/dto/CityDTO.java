package com.keyin.api.dto;

import java.util.List;

public class CityDTO {
    private Long id;
    private String name;
    private String state;
    private Integer population;
    private List<Long> airportIds;   // ✅ reference airports by their IDs only

    // Default constructor (needed for JSON deserialization)
    public CityDTO() {}

    // Full constructor
    public CityDTO(Long id, String name, String state, Integer population, List<Long> airportIds) {
        this.id = id;
        this.name = name;
        this.state = state;
        this.population = population;
        this.airportIds = airportIds;
    }

    // Getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public Integer getPopulation() { return population; }
    public void setPopulation(Integer population) { this.population = population; }

    public List<Long> getAirportIds() { return airportIds; }
    public void setAirportIds(List<Long> airportIds) { this.airportIds = airportIds; }

    // Convenience builder-like method (optional but handy for testing)
    public CityDTO withAirportIds(List<Long> airportIds) {
        this.airportIds = airportIds;
        return this;
    }
}
