package com.keyin.api.controller;

import com.keyin.api.dto.GateDTO;
import com.keyin.api.mapper.GateMapper;
import com.keyin.api.model.Airport;
import com.keyin.api.model.Gate;
import com.keyin.api.repository.AirportRepository;
import com.keyin.api.repository.GateRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/gates")
public class GateController {

    private final GateRepository gateRepository;
    private final AirportRepository airportRepository;

    public GateController(GateRepository gateRepository, AirportRepository airportRepository) {
        this.gateRepository = gateRepository;
        this.airportRepository = airportRepository;
    }

    // List all gates for a given airport
    @GetMapping("/airport/{airportId}")
    public ResponseEntity<List<GateDTO>> getByAirport(@PathVariable Long airportId) {
        List<Gate> gates = gateRepository.findByAirport_Id(airportId);
        List<GateDTO> dtos = gates.stream()
                .map(GateMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
}
