package com.keyin.api.dto;

public class GateDTO {
    private Long id;
    private String code;     // e.g., "A1"
    private Long airportId;  // owning airport

    public GateDTO() {}

    public GateDTO(Long id, String code, Long airportId) {
        this.id = id;
        this.code = code;
        this.airportId = airportId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public Long getAirportId() { return airportId; }
    public void setAirportId(Long airportId) { this.airportId = airportId; }
}
