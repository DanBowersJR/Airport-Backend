package com.keyin.api.model;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(
        name = "airline",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_airline_code", columnNames = "code")
        }
)
public class Airline {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", nullable = false, length = 3, unique = true)
    private String code; // e.g., "AC", "DL", "BA"

    @Column(name = "name", nullable = false, length = 100)
    private String name; // e.g., "Air Canada"

    public Airline() {}

    public Airline(String code, String name) {
        this.code = code;
        this.name = name;
    }

    @PrePersist
    @PreUpdate
    private void normalize() {
        if (code != null) {
            code = code.trim().toUpperCase();
        }
        if (name != null) {
            name = name.trim();
        }
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    // equals & hashCode based on id (standard JPA practice)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Airline)) return false;
        Airline airline = (Airline) o;
        return id != null && Objects.equals(id, airline.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Airline{id=" + id + ", code='" + code + '\'' + ", name='" + name + '\'' + '}';
    }
}
