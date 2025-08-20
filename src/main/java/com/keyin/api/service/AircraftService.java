package com.keyin.api.service;

import com.keyin.api.model.Aircraft;
import com.keyin.api.repository.AircraftRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AircraftService {

    private final AircraftRepository aircraftRepository;

    public AircraftService(AircraftRepository aircraftRepository) {
        this.aircraftRepository = aircraftRepository;
    }

    // Get all aircraft
    public List<Aircraft> getAllAircraft() {
        return aircraftRepository.findAll();
    }

    // Get aircraft by ID (404 if not found)
    public Aircraft getAircraftById(Long id) {
        return aircraftRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Aircraft not found with id: " + id));
    }

    // Save new aircraft
    public Aircraft saveAircraft(Aircraft aircraft) {
        return aircraftRepository.save(aircraft);
    }

    // Update existing aircraft (404 if not found)
    public Aircraft updateAircraft(Long id, Aircraft updatedAircraft) {
        Aircraft existingAircraft = getAircraftById(id); // already throws 404 if missing
        existingAircraft.setType(updatedAircraft.getType());
        existingAircraft.setAirlineName(updatedAircraft.getAirlineName());
        existingAircraft.setNumberOfPassengers(updatedAircraft.getNumberOfPassengers());
        return aircraftRepository.save(existingAircraft);
    }

    // Delete aircraft (return boolean for consistency)
    public boolean deleteAircraft(Long id) {
        if (!aircraftRepository.existsById(id)) {
            return false; // not found
        }
        aircraftRepository.deleteById(id);
        return true; // deleted successfully
    }
}
