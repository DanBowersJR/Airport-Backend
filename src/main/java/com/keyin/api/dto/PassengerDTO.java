package com.keyin.api.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PassengerDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Long cityId;                // ID of the city the passenger belongs to
    private List<Long> aircraftIds;     // IDs of aircraft the passenger has flown on

    // === Constructors ===
    public PassengerDTO() {
        this.aircraftIds = new ArrayList<>();
    }

    public PassengerDTO(Long id, String firstName, String lastName, String phoneNumber,
                        Long cityId, List<Long> aircraftIds) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.cityId = cityId;
        this.aircraftIds = (aircraftIds != null) ? aircraftIds : new ArrayList<>();
    }

    // === Getters & Setters ===
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public Long getCityId() { return cityId; }
    public void setCityId(Long cityId) { this.cityId = cityId; }

    public List<Long> getAircraftIds() { return aircraftIds; }
    public void setAircraftIds(List<Long> aircraftIds) {
        this.aircraftIds = (aircraftIds != null) ? aircraftIds : new ArrayList<>();
    }

    // === Utility methods ===
    public void addAircraft(Long aircraftId) {
        if (this.aircraftIds == null) {
            this.aircraftIds = new ArrayList<>();
        }
        this.aircraftIds.add(aircraftId);
    }

    @Override
    public String toString() {
        return "PassengerDTO{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", cityId=" + cityId +
                ", aircraftIds=" + aircraftIds +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PassengerDTO)) return false;
        PassengerDTO that = (PassengerDTO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(phoneNumber, that.phoneNumber) &&
                Objects.equals(cityId, that.cityId) &&
                Objects.equals(aircraftIds, that.aircraftIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, phoneNumber, cityId, aircraftIds);
    }
}
