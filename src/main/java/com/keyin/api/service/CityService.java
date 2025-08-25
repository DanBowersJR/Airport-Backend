package com.keyin.api.service;

import com.keyin.api.dto.CityDTO;
import com.keyin.api.dto.AirportDTO;
import com.keyin.api.mapper.CityMapper;
import com.keyin.api.mapper.AirportMapper;
import com.keyin.api.model.Airport;
import com.keyin.api.model.City;
import com.keyin.api.repository.AirportRepository;
import com.keyin.api.repository.CityRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CityService {

    private final CityRepository cityRepository;
    private final AirportRepository airportRepository;

    public CityService(CityRepository cityRepository, AirportRepository airportRepository) {
        this.cityRepository = cityRepository;
        this.airportRepository = airportRepository;
    }

    // ✅ Get all cities
    public List<CityDTO> getAllCities() {
        return cityRepository.findAll()
                .stream()
                .map(CityMapper::toDTO)
                .collect(Collectors.toList());
    }

    // ✅ Get city by ID
    public CityDTO getCityById(Long id) {
        City city = cityRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "City not found with id: " + id));
        return CityMapper.toDTO(city);
    }

    // ✅ Create new city
    public CityDTO saveCity(CityDTO dto) {
        List<Airport> airports = (dto.getAirportIds() != null && !dto.getAirportIds().isEmpty())
                ? airportRepository.findAllById(dto.getAirportIds())
                : List.of();

        City city = CityMapper.toEntity(dto, airports);
        City saved = cityRepository.save(city);

        return CityMapper.toDTO(saved);
    }

    // ✅ Update existing city
    public CityDTO updateCity(Long id, CityDTO dto) {
        City existing = cityRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "City not found with id: " + id));

        List<Airport> airports = (dto.getAirportIds() != null && !dto.getAirportIds().isEmpty())
                ? airportRepository.findAllById(dto.getAirportIds())
                : existing.getAirports();

        existing.setName(dto.getName());
        existing.setState(dto.getState());
        existing.setPopulation(dto.getPopulation());
        existing.setAirports(airports);

        City updated = cityRepository.save(existing);
        return CityMapper.toDTO(updated);
    }

    // ✅ Delete city
    public boolean deleteCity(Long id) {
        City city = cityRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "City not found with id: " + id));
        cityRepository.delete(city);
        return true;
    }

    // 🔎 Q1: Get airports by city ID
    public List<AirportDTO> getAirportsByCityId(Long cityId) {
        City city = cityRepository.findById(cityId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "City not found with id: " + cityId));

        List<Airport> airports = city.getAirports();
        if (airports == null || airports.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "No airports found for city with id: " + cityId);
        }

        return airports.stream()
                .map(AirportMapper::toDTO)
                .collect(Collectors.toList());
    }
}
