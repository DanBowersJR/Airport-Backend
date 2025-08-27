package com.keyin.api.dto;

public class AirlineDTO {
    private Long id;
    private String code; // e.g., "AC"
    private String name; // e.g., "Air Canada"

    public AirlineDTO() {}

    public AirlineDTO(Long id, String code, String name) {
        this.id = id;
        this.code = code;
        this.name = name;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
