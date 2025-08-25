package com.keyin.api.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AirportDTO {
    private Long id;
    private String name;
    private String code;
    private Long cityId;               // City this airport belongs to
    private List<Long> aircraftIds;    // Aircraft that use this airport

    // === Constructors ===
    public AirportDTO() {
        this.aircraftIds = new ArrayList<>();
    }

    public AirportDTO(Long id, String name, String code, Long cityId, List<Long> aircraftIds) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.cityId = cityId;
        this.aircraftIds = (aircraftIds != null) ? aircraftIds : new ArrayList<>();
    }

    // === Getters & Setters ===
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

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

    // === Overrides ===
    @Override
    public String toString() {
        return "AirportDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", cityId=" + cityId +
                ", aircraftIds=" + aircraftIds +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AirportDTO)) return false;
        AirportDTO that = (AirportDTO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(code, that.code) &&
                Objects.equals(cityId, that.cityId) &&
                Objects.equals(aircraftIds, that.aircraftIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, code, cityId, aircraftIds);
    }
}
