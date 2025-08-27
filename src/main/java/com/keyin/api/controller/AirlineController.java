package com.keyin.api.controller;

import com.keyin.api.dto.AirlineDTO;
import com.keyin.api.service.AirlineService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/airlines")
public class AirlineController {

    private final AirlineService airlineService;

    public AirlineController(AirlineService airlineService) {
        this.airlineService = airlineService;
    }

    // List all airlines
    @GetMapping
    public ResponseEntity<List<AirlineDTO>> list() {
        return ResponseEntity.ok(airlineService.list());
    }

    // Get by id
    @GetMapping("/{id}")
    public ResponseEntity<AirlineDTO> get(@PathVariable Long id) {
        AirlineDTO dto = airlineService.get(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    // Get by IATA code (e.g., AC, DL, BA)
    @GetMapping("/code/{code}")
    public ResponseEntity<AirlineDTO> getByCode(@PathVariable String code) {
        AirlineDTO dto = airlineService.getByCode(code);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    // Create
    @PostMapping
    public ResponseEntity<AirlineDTO> create(@RequestBody AirlineDTO dto) {
        AirlineDTO saved = airlineService.create(dto);
        return ResponseEntity.created(URI.create("/api/airlines/" + saved.getId()))
                .body(saved);
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean existed = airlineService.delete(id);
        return existed ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    // Optional: basic update
    @PutMapping("/{id}")
    public ResponseEntity<AirlineDTO> update(@PathVariable Long id, @RequestBody AirlineDTO dto) {
        AirlineDTO updated = airlineService.update(id, dto);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }
}
