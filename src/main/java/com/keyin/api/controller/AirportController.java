package com.keyin.api.controller;

import com.keyin.api.dto.AirportDTO;
import com.keyin.api.service.AirportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/airports")
public class AirportController {

    private final AirportService airportService;

    // ✅ Constructor Injection
    public AirportController(AirportService airportService) {
        this.airportService = airportService;
    }

    // ✅ GET all airports
    @GetMapping
    public ResponseEntity<List<AirportDTO>> getAllAirports() {
        List<AirportDTO> airports = airportService.getAllAirports();
        return ResponseEntity.ok(airports);
    }

    // ✅ GET one airport by ID
    @GetMapping("/{id}")
    public ResponseEntity<AirportDTO> getAirportById(@PathVariable Long id) {
        AirportDTO airport = airportService.getAirportById(id);
        return ResponseEntity.ok(airport);
    }

    // ✅ NEW: GET all airports in a given city
    @GetMapping("/city/{cityId}")
    public ResponseEntity<List<AirportDTO>> getAirportsByCity(@PathVariable Long cityId) {
        List<AirportDTO> airports = airportService.getAirportsByCity(cityId);
        return ResponseEntity.ok(airports);
    }

    // ✅ CREATE a new airport
    @PostMapping
    public ResponseEntity<AirportDTO> createAirport(@RequestBody AirportDTO airportDTO) {
        AirportDTO savedAirport = airportService.saveAirport(airportDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAirport);
    }

    // ✅ UPDATE an airport
    @PutMapping("/{id}")
    public ResponseEntity<AirportDTO> updateAirport(
            @PathVariable Long id,
            @RequestBody AirportDTO airportDTO
    ) {
        AirportDTO updatedAirport = airportService.updateAirport(id, airportDTO);
        return ResponseEntity.ok(updatedAirport);
    }

    // ✅ DELETE an airport
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAirport(@PathVariable Long id) {
        boolean deleted = airportService.deleteAirport(id);
        return deleted
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
