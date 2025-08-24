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

    public AirportController(AirportService airportService) {
        this.airportService = airportService;
    }

    // ✅ GET all airports (returns DTOs)
    @GetMapping
    public ResponseEntity<List<AirportDTO>> getAllAirports() {
        return ResponseEntity.ok(airportService.getAllAirports());
    }

    // ✅ GET one airport by ID (returns DTO)
    @GetMapping("/{id}")
    public ResponseEntity<AirportDTO> getAirportById(@PathVariable Long id) {
        return ResponseEntity.ok(airportService.getAirportById(id));
    }

    // ✅ CREATE a new airport (takes DTO)
    @PostMapping
    public ResponseEntity<AirportDTO> createAirport(@RequestBody AirportDTO airportDTO) {
        AirportDTO savedAirport = airportService.saveAirport(airportDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAirport);
    }

    // ✅ UPDATE an airport (takes DTO)
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
        return deleted ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
