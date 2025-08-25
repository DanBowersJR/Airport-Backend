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

    // ✅ Get all aircraft
    public List<AircraftDTO> getAllAircraft() {
        return aircraftRepository.findAll()
                .stream()
                .map(AircraftMapper::toDTO)
                .collect(Collectors.toList());
    }

    // ✅ Get aircraft by ID
    public AircraftDTO getAircraftById(Long id) {
        Aircraft aircraft = aircraftRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Aircraft not found with id: " + id));
        return AircraftMapper.toDTO(aircraft);
    }

    // ✅ Create new aircraft
    public AircraftDTO saveAircraft(AircraftDTO dto) {
        if (dto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Aircraft data must not be null");
        }

        // Load airports if provided
        List<Airport> airports = (dto.getAirportIds() != null && !dto.getAirportIds().isEmpty())
                ? airportRepository.findAllById(dto.getAirportIds())
                : List.of();

        // Load passengers if provided
        List<Passenger> passengers = (dto.getPassengerIds() != null && !dto.getPassengerIds().isEmpty())
                ? passengerRepository.findAllById(dto.getPassengerIds())
                : List.of();

        // Map DTO → Entity
        Aircraft aircraft = AircraftMapper.toEntity(dto, airports, passengers);

        // Save and return DTO
        Aircraft saved = aircraftRepository.save(aircraft);
        return AircraftMapper.toDTO(saved);
    }

    // ✅ Update existing aircraft
    public AircraftDTO updateAircraft(Long id, AircraftDTO dto) {
        Aircraft existing = aircraftRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Aircraft not found with id: " + id));

        if (dto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Aircraft data must not be null");
        }

        // Update fields if provided
        if (dto.getType() != null) existing.setType(dto.getType());
        if (dto.getAirlineName() != null) existing.setAirlineName(dto.getAirlineName());
        if (dto.getNumberOfPassengers() > 0) existing.setNumberOfPassengers(dto.getNumberOfPassengers());

        // Update airports if provided
        if (dto.getAirportIds() != null) {
            List<Airport> airports = airportRepository.findAllById(dto.getAirportIds());
            existing.setAirports(airports);
        }

        // Update passengers if provided
        if (dto.getPassengerIds() != null) {
            List<Passenger> passengers = passengerRepository.findAllById(dto.getPassengerIds());
            existing.setPassengers(passengers);
        }

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

    // ✅ Q3: Get airports for aircraft
    public List<AirportDTO> getAirportsForAircraft(Long id) {
        Aircraft aircraft = aircraftRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Aircraft not found with id: " + id));

        return aircraft.getAirports()
                .stream()
                .map(AirportMapper::toDTO)
                .collect(Collectors.toList());
    }
}
