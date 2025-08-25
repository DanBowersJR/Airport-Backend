package com.keyin.api.controller;

import com.keyin.api.dto.AircraftDTO;
import com.keyin.api.dto.AirportDTO;
import com.keyin.api.service.AircraftService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/aircraft")
public class AircraftController {

    private final AircraftService aircraftService;

    // ✅ Constructor injection
    public AircraftController(AircraftService aircraftService) {
        this.aircraftService = aircraftService;
    }

    // ✅ GET all aircraft (returns list of DTOs)
    @GetMapping
    public ResponseEntity<List<AircraftDTO>> getAllAircraft() {
        List<AircraftDTO> aircraftList = aircraftService.getAllAircraft();
        return ResponseEntity.ok(aircraftList);
    }

    // ✅ GET single aircraft by ID (returns DTO)
    @GetMapping("/{id}")
    public ResponseEntity<AircraftDTO> getAircraftById(@PathVariable Long id) {
        AircraftDTO aircraft = aircraftService.getAircraftById(id);
        return ResponseEntity.ok(aircraft);
    }

    // ✅ CREATE new aircraft (returns created DTO)
    @PostMapping
    public ResponseEntity<AircraftDTO> createAircraft(@RequestBody AircraftDTO aircraftDTO) {
        AircraftDTO savedAircraft = aircraftService.saveAircraft(aircraftDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAircraft);
    }

    // ✅ UPDATE existing aircraft (returns updated DTO)
    @PutMapping("/{id}")
    public ResponseEntity<AircraftDTO> updateAircraft(
            @PathVariable Long id,
            @RequestBody AircraftDTO aircraftDTO
    ) {
        AircraftDTO updatedAircraft = aircraftService.updateAircraft(id, aircraftDTO);
        return ResponseEntity.ok(updatedAircraft);
    }

    // ✅ DELETE aircraft (returns 204 if deleted, 404 if not found)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAircraft(@PathVariable Long id) {
        boolean deleted = aircraftService.deleteAircraft(id);
        return deleted
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    // 🔎 Q3: What airports does this aircraft use? (takeoff/landing)
    @GetMapping("/{id}/airports")
    public ResponseEntity<List<AirportDTO>> getAirportsForAircraft(@PathVariable Long id) {
        List<AirportDTO> airports = aircraftService.getAirportsForAircraft(id);
        return ResponseEntity.ok(airports);
    }
}
