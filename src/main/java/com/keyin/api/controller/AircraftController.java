package com.keyin.api.controller;

import com.keyin.api.model.Aircraft;
import com.keyin.api.service.AircraftService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/aircraft")
public class AircraftController {

    private final AircraftService aircraftService;

    // constructor injection
    public AircraftController(AircraftService aircraftService) {
        this.aircraftService = aircraftService;
    }

    // ✅ GET all aircraft
    @GetMapping
    public ResponseEntity<List<Aircraft>> getAllAircraft() {
        return ResponseEntity.ok(aircraftService.getAllAircraft());
    }

    // ✅ GET one aircraft by ID
    @GetMapping("/{id}")
    public ResponseEntity<Aircraft> getAircraftById(@PathVariable Long id) {
        Aircraft aircraft = aircraftService.getAircraftById(id);
        return ResponseEntity.ok(aircraft);
    }

    // ✅ CREATE a new aircraft
    @PostMapping
    public ResponseEntity<Aircraft> createAircraft(@RequestBody Aircraft aircraft) {
        Aircraft savedAircraft = aircraftService.saveAircraft(aircraft);
        return new ResponseEntity<>(savedAircraft, HttpStatus.CREATED);
    }

    // ✅ UPDATE existing aircraft
    @PutMapping("/{id}")
    public ResponseEntity<Aircraft> updateAircraft(@PathVariable Long id, @RequestBody Aircraft aircraft) {
        Aircraft updatedAircraft = aircraftService.updateAircraft(id, aircraft);
        return ResponseEntity.ok(updatedAircraft);
    }

    // ✅ DELETE aircraft
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAircraft(@PathVariable Long id) {
        boolean deleted = aircraftService.deleteAircraft(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
