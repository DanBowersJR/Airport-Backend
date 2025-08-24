package com.keyin.api.dto;

import java.util.List;

public class AircraftDTO {
    private Long id;
    private String type;
    private String airlineName;
    private int numberOfPassengers;
    private Long airportId; // reference airport by ID
    private List<Long> passengerIds; // just IDs to avoid recursion

    public AircraftDTO() {}

    public AircraftDTO(Long id, String type, String airlineName, int numberOfPassengers, Long airportId, List<Long> passengerIds) {
        this.id = id;
        this.type = type;
        this.airlineName = airlineName;
        this.numberOfPassengers = numberOfPassengers;
        this.airportId = airportId;
        this.passengerIds = passengerIds;
    }

    // Getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getAirlineName() { return airlineName; }
    public void setAirlineName(String airlineName) { this.airlineName = airlineName; }

    public int getNumberOfPassengers() { return numberOfPassengers; }
    public void setNumberOfPassengers(int numberOfPassengers) { this.numberOfPassengers = numberOfPassengers; }

    public Long getAirportId() { return airportId; }
    public void setAirportId(Long airportId) { this.airportId = airportId; }

    public List<Long> getPassengerIds() { return passengerIds; }
    public void setPassengerIds(List<Long> passengerIds) { this.passengerIds = passengerIds; }
}
