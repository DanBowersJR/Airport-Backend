package com.keyin.api.model;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "gate")
public class Gate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 5)
    private String code; // Example: "A1", "B12"

    @ManyToOne
    @JoinColumn(name = "airport_id", nullable = false)
    private Airport airport;

    public Gate() {}

    public Gate(String code, Airport airport) {
        this.code = code;
        this.airport = airport;
    }

    // Getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public Airport getAirport() { return airport; }
    public void setAirport(Airport airport) { this.airport = airport; }

    // equals & hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Gate)) return false;
        Gate gate = (Gate) o;
        return id != null && Objects.equals(id, gate.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Gate{id=" + id + ", code='" + code + '\'' + ", airport=" + airport.getName() + '}';
    }
}
