package com.keyin.api.controller;

import com.keyin.api.dto.PassengerDTO;
import com.keyin.api.dto.AircraftDTO;
import com.keyin.api.dto.AirportDTO;
import com.keyin.api.service.PassengerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/passengers")
public class PassengerController {

    private final PassengerService passengerService;

    // ✅ Constructor injection
    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    // ✅ CREATE Passenger (accepts DTO, returns DTO)
    @PostMapping
    public ResponseEntity<PassengerDTO> createPassenger(@RequestBody PassengerDTO passengerDTO) {
        PassengerDTO savedPassenger = passengerService.savePassenger(passengerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPassenger);
    }

    // ✅ READ ALL Passengers (returns list of DTOs)
    @GetMapping
    public ResponseEntity<List<PassengerDTO>> getAllPassengers() {
        return ResponseEntity.ok(passengerService.getAllPassengers());
    }

    // ✅ READ ONE Passenger by ID (returns DTO)
    @GetMapping("/{id}")
    public ResponseEntity<PassengerDTO> getPassengerById(@PathVariable Long id) {
        return ResponseEntity.ok(passengerService.getPassengerById(id));
    }

    // ✅ UPDATE Passenger (accepts DTO, returns DTO)
    @PutMapping("/{id}")
    public ResponseEntity<PassengerDTO> updatePassenger(
            @PathVariable Long id,
            @RequestBody PassengerDTO passengerDTO
    ) {
        return ResponseEntity.ok(passengerService.updatePassenger(id, passengerDTO));
    }

    // ✅ DELETE Passenger (returns 204 if deleted, 404 if not found)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePassenger(@PathVariable Long id) {
        boolean deleted = passengerService.deletePassenger(id);
        return deleted
                ? ResponseEntity.noContent().build()   // 204 No Content
                : ResponseEntity.notFound().build();   // 404 Not Found
    }

    // 🔎 Q2: What aircraft has this passenger flown on? (returns AircraftDTOs)
    @GetMapping("/{id}/aircraft")
    public ResponseEntity<List<AircraftDTO>> getAircraftByPassenger(@PathVariable Long id) {
        return ResponseEntity.ok(passengerService.getAircraftByPassenger(id));
    }

    // 🔎 Q4: What airports has this passenger used? (returns AirportDTOs)
    @GetMapping("/{id}/airports")
    public ResponseEntity<Set<AirportDTO>> getAirportsUsedByPassenger(@PathVariable Long id) {
        return ResponseEntity.ok(passengerService.getAirportsUsedByPassenger(id));
    }
}
