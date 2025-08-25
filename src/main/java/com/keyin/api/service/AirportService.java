package com.keyin.api.service;

import com.keyin.api.dto.AirportDTO;
import com.keyin.api.mapper.AirportMapper;
import com.keyin.api.model.Airport;
import com.keyin.api.model.Aircraft;
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

    // ✅ NEW: Get all airports by city ID
    public List<AirportDTO> getAirportsByCity(Long cityId) {
        City city = cityRepository.findById(cityId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "City not found with id: " + cityId));

        return airportRepository.findByCityId(cityId)
                .stream()
                .map(AirportMapper::toDTO)
                .collect(Collectors.toList());
    }

    // ✅ Create new airport
    public AirportDTO saveAirport(AirportDTO dto) {
        if (dto.getCityId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "City ID is required to create an airport");
        }

        City city = cityRepository.findById(dto.getCityId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "City not found with id: " + dto.getCityId()));

        List<Aircraft> aircraftList = (dto.getAircraftIds() != null && !dto.getAircraftIds().isEmpty())
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

        // Update fields
        if (dto.getName() != null) existing.setName(dto.getName());
        if (dto.getCode() != null) existing.setCode(dto.getCode());

        // Update city if provided
        if (dto.getCityId() != null) {
            City city = cityRepository.findById(dto.getCityId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "City not found with id: " + dto.getCityId()));
            existing.setCity(city);
        }

        // Update aircraft list if provided
        if (dto.getAircraftIds() != null) {
            List<Aircraft> aircraftList = aircraftRepository.findAllById(dto.getAircraftIds());
            existing.setAircraftList(aircraftList);
        }

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
