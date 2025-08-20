package com.keyin.api.service;

import com.keyin.api.model.Airport;
import com.keyin.api.repository.AirportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AirportService {

    @Autowired
    private AirportRepository airportRepository;

    // Get all airports
    public List<Airport> getAllAirports() {
        return airportRepository.findAll();
    }

    // Get one airport by id
    public Optional<Airport> getAirportById(Long id) {
        return airportRepository.findById(id);
    }

    // Save or update an airport
    public Airport saveAirport(Airport airport) {
        return airportRepository.save(airport);
    }

    // Delete an airport
    public void deleteAirport(Long id) {
        airportRepository.deleteById(id);
    }
}
