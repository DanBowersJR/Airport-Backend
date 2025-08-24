package com.keyin.api.controller;

import com.keyin.api.dto.CityDTO;
import com.keyin.api.dto.AirportDTO;
import com.keyin.api.service.CityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cities")
public class CityController {

    private final CityService cityService;

    // ✅ Constructor injection
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    // ✅ GET all cities (DTOs only)
    @GetMapping
    public ResponseEntity<List<CityDTO>> getAllCities() {
        return ResponseEntity.ok(cityService.getAllCities());
    }

    // ✅ GET one city by ID (DTO only)
    @GetMapping("/{id}")
    public ResponseEntity<CityDTO> getCityById(@PathVariable Long id) {
        return ResponseEntity.ok(cityService.getCityById(id));
    }

    // ✅ CREATE new city (accepts DTO, returns DTO)
    @PostMapping
    public ResponseEntity<CityDTO> createCity(@RequestBody CityDTO cityDTO) {
        CityDTO savedCity = cityService.saveCity(cityDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCity);
    }

    // ✅ UPDATE existing city (accepts DTO, returns DTO)
    @PutMapping("/{id}")
    public ResponseEntity<CityDTO> updateCity(
            @PathVariable Long id,
            @RequestBody CityDTO updatedCityDTO
    ) {
        CityDTO updatedCity = cityService.updateCity(id, updatedCityDTO);
        return ResponseEntity.ok(updatedCity);
    }

    // ✅ DELETE a city (returns 204 if deleted, 404 if not found)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCity(@PathVariable Long id) {
        boolean deleted = cityService.deleteCity(id);
        return deleted
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    // 🔎 Q1: What airports are there in each city?
    // Returns list of AirportDTOs (404 if none exist)
    @GetMapping("/{id}/airports")
    public ResponseEntity<List<AirportDTO>> getAirportsByCity(@PathVariable Long id) {
        List<AirportDTO> airports = cityService.getAirportsByCityId(id);
        return ResponseEntity.ok(airports);
    }
}
