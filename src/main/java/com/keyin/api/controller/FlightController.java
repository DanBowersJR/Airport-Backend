package com.keyin.api.controller;

import com.keyin.api.dto.FlightDTO;
import com.keyin.api.service.FlightService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/flights")
public class FlightController {

    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    // --- Create ---
    @PostMapping
    public ResponseEntity<?> create(@RequestBody FlightDTO dto) {
        try {
            FlightDTO saved = flightService.create(dto);
            return ResponseEntity
                    .created(URI.create("/api/flights/" + saved.getId()))
                    .body(saved);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    // --- Read one ---
    @GetMapping("/{id}")
    public ResponseEntity<FlightDTO> get(@PathVariable Long id) {
        FlightDTO dto = flightService.get(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    // --- Delete ---
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        flightService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // --- Lists for the UI ---

    // All flights for an airport (arrivals + departures)
    @GetMapping
    public ResponseEntity<List<FlightDTO>> byAirport(@RequestParam("airportId") Long airportId) {
        return ResponseEntity.ok(flightService.listByAirport(airportId));
    }

    // Arrivals board
    @GetMapping("/arrivals")
    public ResponseEntity<List<FlightDTO>> arrivals(@RequestParam("airportId") Long airportId) {
        return ResponseEntity.ok(flightService.listArrivals(airportId));
    }

    // Departures board
    @GetMapping("/departures")
    public ResponseEntity<List<FlightDTO>> departures(@RequestParam("airportId") Long airportId) {
        return ResponseEntity.ok(flightService.listDepartures(airportId));
    }
}
