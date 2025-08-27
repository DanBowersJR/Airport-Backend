package com.keyin.api.controller;

import com.keyin.api.dto.GateDTO;
import com.keyin.api.service.GateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gates")
public class GateController {

    private final GateService gateService;

    public GateController(GateService gateService) {
        this.gateService = gateService;
    }

    // List all gates across all airports
    @GetMapping
    public ResponseEntity<List<GateDTO>> getAll() {
        return ResponseEntity.ok(gateService.listAll());
    }

    // List gates for a specific airport
    @GetMapping("/airport/{airportId}")
    public ResponseEntity<List<GateDTO>> byAirport(@PathVariable Long airportId) {
        return ResponseEntity.ok(gateService.listByAirport(airportId));
    }

    // Get one gate
    @GetMapping("/{id}")
    public ResponseEntity<GateDTO> get(@PathVariable Long id) {
        GateDTO dto = gateService.get(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    // Create a gate (requires airportId in body)
    @PostMapping
    public ResponseEntity<?> create(@RequestBody GateDTO dto) {
        try {
            GateDTO saved = gateService.create(dto);
            return ResponseEntity.ok(saved);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    // Delete a gate
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean existed = gateService.delete(id);
        return existed ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
