package com.keyin.api.dto;

import java.util.List;

public class AirportDTO {
    private Long id;
    private String name;
    private String code;
    private Long cityId;              // reference city by ID
    private List<Long> aircraftIds;   // reference aircraft by IDs

    // Constructors
    public AirportDTO() {}

    public AirportDTO(Long id, String name, String code, Long cityId, List<Long> aircraftIds) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.cityId = cityId;
        this.aircraftIds = aircraftIds;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }

    public Long getCityId() {
        return cityId;
    }
    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public List<Long> getAircraftIds() {
        return aircraftIds;
    }
    public void setAircraftIds(List<Long> aircraftIds) {
        this.aircraftIds = aircraftIds;
    }
}
