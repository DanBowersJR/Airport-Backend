package com.keyin.api.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(
        name = "flight",
        indexes = {
                // Helps queries like: by airport + type ordered by time
                @Index(name = "idx_flight_airport_type_time", columnList = "airport_id,type,scheduled_time")
        }
)
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "flight_number", nullable = false, length = 10)
    private String flightNumber; // e.g., "AC123"

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 15)
    private FlightType type; // ARRIVAL or DEPARTURE

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 15)
    private FlightStatus status = FlightStatus.SCHEDULED;

    @Column(name = "scheduled_time")
    private LocalDateTime scheduledTime;

    @Column(name = "actual_time")
    private LocalDateTime actualTime;

    // IATA airport codes for display/filtering only (not FKs)
    @Column(name = "origin_code", length = 4)
    private String originCode;

    @Column(name = "destination_code", length = 4)
    private String destinationCode;

    // Relationships
    @ManyToOne
    @JoinColumn(name = "airport_id", nullable = false)
    private Airport airport;

    @ManyToOne
    @JoinColumn(name = "airline_id", nullable = false)
    private Airline airline;

    @ManyToOne
    @JoinColumn(name = "gate_id")
    private Gate gate;

    @ManyToOne
    @JoinColumn(name = "aircraft_id")
    private Aircraft aircraft;

    public Flight() {}

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFlightNumber() { return flightNumber; }
    public void setFlightNumber(String flightNumber) { this.flightNumber = flightNumber; }

    public FlightType getType() { return type; }
    public void setType(FlightType type) { this.type = type; }

    public FlightStatus getStatus() { return status; }
    public void setStatus(FlightStatus status) { this.status = status; }

    public LocalDateTime getScheduledTime() { return scheduledTime; }
    public void setScheduledTime(LocalDateTime scheduledTime) { this.scheduledTime = scheduledTime; }

    public LocalDateTime getActualTime() { return actualTime; }
    public void setActualTime(LocalDateTime actualTime) { this.actualTime = actualTime; }

    public String getOriginCode() { return originCode; }
    public void setOriginCode(String originCode) { this.originCode = originCode; }

    public String getDestinationCode() { return destinationCode; }
    public void setDestinationCode(String destinationCode) { this.destinationCode = destinationCode; }

    public Airport getAirport() { return airport; }
    public void setAirport(Airport airport) { this.airport = airport; }

    public Airline getAirline() { return airline; }
    public void setAirline(Airline airline) { this.airline = airline; }

    public Gate getGate() { return gate; }
    public void setGate(Gate gate) { this.gate = gate; }

    public Aircraft getAircraft() { return aircraft; }
    public void setAircraft(Aircraft aircraft) { this.aircraft = aircraft; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Flight)) return false;
        Flight flight = (Flight) o;
        return id != null && Objects.equals(id, flight.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Flight{id=" + id +
                ", flightNumber='" + flightNumber + '\'' +
                ", type=" + type +
                ", status=" + status + '}';
    }
}
