package com.keyin.api.service;

import com.keyin.api.dto.FlightDTO;
import com.keyin.api.mapper.FlightMapper;
import com.keyin.api.model.*;
import com.keyin.api.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlightService {

    private final FlightRepository flightRepository;
    private final AirportRepository airportRepository;
    private final AirlineRepository airlineRepository;
    private final GateRepository gateRepository;
    private final AircraftRepository aircraftRepository;

    public FlightService(FlightRepository flightRepository,
                         AirportRepository airportRepository,
                         AirlineRepository airlineRepository,
                         GateRepository gateRepository,
                         AircraftRepository aircraftRepository) {
        this.flightRepository = flightRepository;
        this.airportRepository = airportRepository;
        this.airlineRepository = airlineRepository;
        this.gateRepository = gateRepository;
        this.aircraftRepository = aircraftRepository;
    }

    // ---------- CRUD-ish ----------

    @Transactional
    public FlightDTO create(FlightDTO dto) {
        Flight flight = FlightMapper.toEntity(dto);
        attachRelations(flight, dto);
        Flight saved = flightRepository.save(flight);
        return FlightMapper.toDTO(saved);
    }

    public FlightDTO get(Long id) {
        return flightRepository.findById(id)
                .map(FlightMapper::toDTO)
                .orElse(null);
    }

    @Transactional
    public void delete(Long id) {
        if (flightRepository.existsById(id)) {
            flightRepository.deleteById(id);
        }
    }

    // ---------- Queries for UI (arrivals/departures boards) ----------

    public List<FlightDTO> listByAirport(Long airportId) {
        return flightRepository.findByAirport_IdOrderByScheduledTimeAsc(airportId)
                .stream().map(FlightMapper::toDTO).collect(Collectors.toList());
    }

    public List<FlightDTO> listArrivals(Long airportId) {
        return flightRepository.findByAirport_IdAndTypeOrderByScheduledTimeAsc(airportId, FlightType.ARRIVAL)
                .stream().map(FlightMapper::toDTO).collect(Collectors.toList());
    }

    public List<FlightDTO> listDepartures(Long airportId) {
        return flightRepository.findByAirport_IdAndTypeOrderByScheduledTimeAsc(airportId, FlightType.DEPARTURE)
                .stream().map(FlightMapper::toDTO).collect(Collectors.toList());
    }

    // ---------- Helpers ----------

    /**
     * Resolve IDs from DTO and attach managed entities to the Flight.
     * Throws IllegalArgumentException with a clear message if something is missing.
     */
    private void attachRelations(Flight flight, FlightDTO dto) {
        if (dto.getAirportId() == null) {
            throw new IllegalArgumentException("airportId is required");
        }
        if (dto.getAirlineId() == null) {
            throw new IllegalArgumentException("airlineId is required");
        }

        Airport airport = airportRepository.findById(dto.getAirportId())
                .orElseThrow(() -> new IllegalArgumentException("Airport not found: " + dto.getAirportId()));
        Airline airline = airlineRepository.findById(dto.getAirlineId())
                .orElseThrow(() -> new IllegalArgumentException("Airline not found: " + dto.getAirlineId()));

        flight.setAirport(airport);
        flight.setAirline(airline);

        if (dto.getGateId() != null) {
            Gate gate = gateRepository.findById(dto.getGateId())
                    .orElseThrow(() -> new IllegalArgumentException("Gate not found: " + dto.getGateId()));
            flight.setGate(gate);
        } else {
            flight.setGate(null);
        }

        if (dto.getAircraftId() != null) {
            Aircraft aircraft = aircraftRepository.findById(dto.getAircraftId())
                    .orElseThrow(() -> new IllegalArgumentException("Aircraft not found: " + dto.getAircraftId()));
            flight.setAircraft(aircraft);
        } else {
            flight.setAircraft(null);
        }
    }
}
