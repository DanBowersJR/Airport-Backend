package com.keyin.api.dto;

import java.util.List;

public class AircraftDTO {
    private Long id;
    private String type;
    private String airlineName;
    private int numberOfPassengers;

    private List<Long> airportIds;    // ✅ list of airports
    private List<Long> passengerIds;  // ✅ list of passengers

    public AircraftDTO() {}

    public AircraftDTO(Long id, String type, String airlineName, int numberOfPassengers,
                       List<Long> airportIds, List<Long> passengerIds) {
        this.id = id;
        this.type = type;
        this.airlineName = airlineName;
        this.numberOfPassengers = numberOfPassengers;
        this.airportIds = airportIds;
        this.passengerIds = passengerIds;
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

    public List<Long> getAirportIds() { return airportIds; }
    public void setAirportIds(List<Long> airportIds) { this.airportIds = airportIds; }

    public List<Long> getPassengerIds() { return passengerIds; }
    public void setPassengerIds(List<Long> passengerIds) { this.passengerIds = passengerIds; }
}
