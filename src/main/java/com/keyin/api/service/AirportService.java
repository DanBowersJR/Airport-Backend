package com.keyin.api.service;

import com.keyin.api.model.Airport;
import com.keyin.api.repository.AirportRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AirportService {

    private final AirportRepository airportRepository;

    public AirportService(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }

    // ✅ Get all airports
    public List<Airport> getAllAirports() {
        return airportRepository.findAll();
    }

    // ✅ Get airport by ID (404 if not found)
    public Airport getAirportById(Long id) {
        return airportRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Airport not found with id: " + id));
    }

    // ✅ Create new airport
    public Airport saveAirport(Airport airport) {
        return airportRepository.save(airport);
    }

    // ✅ Update existing airport (404 if not found)
    public Airport updateAirport(Long id, Airport updatedAirport) {
        Airport existingAirport = getAirportById(id); // already throws 404 if missing
        existingAirport.setName(updatedAirport.getName());
        existingAirport.setCode(updatedAirport.getCode());
        // Add more setters if Airport has extra fields
        return airportRepository.save(existingAirport);
    }

    // ✅ Delete airport (returns boolean for controller)
    public boolean deleteAirport(Long id) {
        if (!airportRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Airport not found with id: " + id);
        }
        airportRepository.deleteById(id);
        return true;
    }
}
