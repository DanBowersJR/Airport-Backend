package com.keyin.api.service;

import com.keyin.api.dto.AirportDTO;
import com.keyin.api.mapper.AirportMapper;
import com.keyin.api.model.Aircraft;
import com.keyin.api.model.Airport;
import com.keyin.api.model.City;
import com.keyin.api.repository.AircraftRepository;
import com.keyin.api.repository.AirportRepository;
import com.keyin.api.repository.CityRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AirportService {

    private final AirportRepository airportRepository;
    private final CityRepository cityRepository;
    private final AircraftRepository aircraftRepository;

    public AirportService(AirportRepository airportRepository,
                          CityRepository cityRepository,
                          AircraftRepository aircraftRepository) {
        this.airportRepository = airportRepository;
        this.cityRepository = cityRepository;
        this.aircraftRepository = aircraftRepository;
    }

    // ✅ Get all airports (DTOs)
    public List<AirportDTO> getAllAirports() {
        return airportRepository.findAll()
                .stream()
                .map(AirportMapper::toDTO)
                .collect(Collectors.toList());
    }

    // ✅ Get one airport by ID (DTO)
    public AirportDTO getAirportById(Long id) {
        Airport airport = airportRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Airport not found with id: " + id));
        return AirportMapper.toDTO(airport);
    }

    // ✅ Create new airport
    public AirportDTO saveAirport(AirportDTO dto) {
        // Find linked city
        City city = cityRepository.findById(dto.getCityId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "City not found with id: " + dto.getCityId()));

        // Find linked aircraft (optional list)
        List<Aircraft> aircraftList = (dto.getAircraftIds() != null)
                ? aircraftRepository.findAllById(dto.getAircraftIds())
                : List.of();

        Airport airport = AirportMapper.toEntity(dto, city, aircraftList);
        Airport saved = airportRepository.save(airport);

        return AirportMapper.toDTO(saved);
    }

    // ✅ Update airport
    public AirportDTO updateAirport(Long id, AirportDTO dto) {
        Airport existing = airportRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Airport not found with id: " + id));

        // Update city if provided
        City city = null;
        if (dto.getCityId() != null) {
            city = cityRepository.findById(dto.getCityId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "City not found with id: " + dto.getCityId()));
        }

        // Update aircraft list if provided
        List<Aircraft> aircraftList = (dto.getAircraftIds() != null)
                ? aircraftRepository.findAllById(dto.getAircraftIds())
                : existing.getAircraftList();

        // Update fields
        existing.setName(dto.getName());
        existing.setCode(dto.getCode());
        if (city != null) existing.setCity(city);
        existing.setAircraftList(aircraftList);

        Airport updated = airportRepository.save(existing);
        return AirportMapper.toDTO(updated);
    }

    // ✅ Delete airport
    public boolean deleteAirport(Long id) {
        Airport airport = airportRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Airport not found with id: " + id));

        airportRepository.delete(airport);
        return true;
    }
}
