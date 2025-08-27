package com.keyin.api.repository;

import com.keyin.api.model.Flight;
import com.keyin.api.model.FlightType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    // All flights for an airport (arrivals + departures)
    List<Flight> findByAirport_IdOrderByScheduledTimeAsc(Long airportId);

    // Arrivals for an airport
    List<Flight> findByAirport_IdAndTypeOrderByScheduledTimeAsc(Long airportId, FlightType type);
}
