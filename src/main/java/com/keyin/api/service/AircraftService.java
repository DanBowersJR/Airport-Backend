package com.keyin.api.service;

import com.keyin.api.dto.AircraftDTO;
import com.keyin.api.dto.AirportDTO;
import com.keyin.api.mapper.AircraftMapper;
import com.keyin.api.mapper.AirportMapper;
import com.keyin.api.model.Aircraft;
import com.keyin.api.model.Airport;
import com.keyin.api.model.Passenger;
import com.keyin.api.repository.AircraftRepository;
import com.keyin.api.repository.AirportRepository;
import com.keyin.api.repository.PassengerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AircraftService {

    private final AircraftRepository aircraftRepository;
    private final AirportRepository airportRepository;
    private final PassengerRepository passengerRepository;

    public AircraftService(AircraftRepository aircraftRepository,
                           AirportRepository airportRepository,
                           PassengerRepository passengerRepository) {
        this.aircraftRepository = aircraftRepository;
        this.airportRepository = airportRepository;
        this.passengerRepository = passengerRepository;
    }

    // ✅ Get all aircraft as DTOs
    public List<AircraftDTO> getAllAircraft() {
        return aircraftRepository.findAll()
                .stream()
                .map(AircraftMapper::toDTO)
                .collect(Collectors.toList());
    }

    // ✅ Get aircraft by ID (returns DTO)
    public AircraftDTO getAircraftById(Long id) {
        Aircraft aircraft = aircraftRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Aircraft not found with id: " + id));
        return AircraftMapper.toDTO(aircraft);
    }

    // ✅ Save new aircraft
    public AircraftDTO saveAircraft(AircraftDTO dto) {
        // Lookup airport
        Airport airport = airportRepository.findById(dto.getAirportId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Airport not found with id: " + dto.getAirportId()));

        // Lookup passengers
        List<Passenger> passengers = (dto.getPassengerIds() != null && !dto.getPassengerIds().isEmpty())
                ? passengerRepository.findAllById(dto.getPassengerIds())
                : List.of();

        // Map DTO -> Entity
        Aircraft aircraft = AircraftMapper.toEntity(dto, airport, passengers);
        Aircraft saved = aircraftRepository.save(aircraft);

        return AircraftMapper.toDTO(saved);
    }

    // ✅ Update existing aircraft
    public AircraftDTO updateAircraft(Long id, AircraftDTO dto) {
        Aircraft existing = aircraftRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Aircraft not found with id: " + id));

        // Update airport if provided
        if (dto.getAirportId() != null) {
            Airport airport = airportRepository.findById(dto.getAirportId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Airport not found with id: " + dto.getAirportId()));
            existing.setAirport(airport);
        }

        // Update passengers if provided, otherwise keep existing
        List<Passenger> passengers = (dto.getPassengerIds() != null && !dto.getPassengerIds().isEmpty())
                ? passengerRepository.findAllById(dto.getPassengerIds())
                : existing.getPassengers();

        // Update basic fields
        existing.setType(dto.getType());
        existing.setAirlineName(dto.getAirlineName());
        existing.setNumberOfPassengers(dto.getNumberOfPassengers());
        existing.setPassengers(passengers);

        Aircraft updated = aircraftRepository.save(existing);
        return AircraftMapper.toDTO(updated);
    }

    // ✅ Delete aircraft
    public boolean deleteAircraft(Long id) {
        if (!aircraftRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aircraft not found with id: " + id);
        }
        aircraftRepository.deleteById(id);
        return true;
    }

    // 🔎 Q3: What airport does an aircraft take off from / land at?
    public AirportDTO getAirportForAircraft(Long id) {
        Aircraft aircraft = aircraftRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Aircraft not found with id: " + id));

        Airport airport = aircraft.getAirport();
        if (airport == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "No airport assigned for aircraft with id: " + id);
        }

        return AirportMapper.toDTO(airport); // ✅ Always return DTO
    }
}
