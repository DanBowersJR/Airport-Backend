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

    // ✅ CREATE Passenger
    @PostMapping
    public ResponseEntity<PassengerDTO> createPassenger(@RequestBody PassengerDTO passengerDTO) {
        PassengerDTO savedPassenger = passengerService.savePassenger(passengerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPassenger);
    }

    // ✅ READ ALL Passengers
    @GetMapping
    public ResponseEntity<List<PassengerDTO>> getAllPassengers() {
        return ResponseEntity.ok(passengerService.getAllPassengers());
    }

    // ✅ READ ONE Passenger by ID
    @GetMapping("/{id}")
    public ResponseEntity<PassengerDTO> getPassengerById(@PathVariable Long id) {
        return ResponseEntity.ok(passengerService.getPassengerById(id));
    }

    // ✅ UPDATE Passenger
    @PutMapping("/{id}")
    public ResponseEntity<PassengerDTO> updatePassenger(
            @PathVariable Long id,
            @RequestBody PassengerDTO passengerDTO
    ) {
        return ResponseEntity.ok(passengerService.updatePassenger(id, passengerDTO));
    }

    // ✅ DELETE Passenger
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePassenger(@PathVariable Long id) {
        boolean deleted = passengerService.deletePassenger(id);
        return deleted
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    // 🔎 Q2: What aircraft has this passenger flown on?
    @GetMapping("/{id}/aircraft")
    public ResponseEntity<List<AircraftDTO>> getAircraftByPassenger(@PathVariable Long id) {
        List<AircraftDTO> aircraftList = passengerService.getAircraftByPassenger(id);
        return ResponseEntity.ok(aircraftList);
    }

    // 🔎 Q4: What airports has this passenger used?
    @GetMapping("/{id}/airports")
    public ResponseEntity<Set<AirportDTO>> getAirportsUsedByPassenger(@PathVariable Long id) {
        Set<AirportDTO> airports = passengerService.getAirportsUsedByPassenger(id);
        return ResponseEntity.ok(airports);
    }
}
