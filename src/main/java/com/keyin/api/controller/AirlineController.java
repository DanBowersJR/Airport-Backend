package com.keyin.api.controller;

import com.keyin.api.dto.AirlineDTO;
import com.keyin.api.mapper.AirlineMapper;
import com.keyin.api.model.Airline;
import com.keyin.api.repository.AirlineRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/airlines")
public class AirlineController {

    private final AirlineRepository airlineRepository;

    public AirlineController(AirlineRepository airlineRepository) {
        this.airlineRepository = airlineRepository;
    }

    @GetMapping
    public List<AirlineDTO> list() {
        return airlineRepository.findAll().stream()
                .map(AirlineMapper::toDTO)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<AirlineDTO> create(@RequestBody AirlineDTO dto) {
        Airline saved = airlineRepository.save(AirlineMapper.toEntity(dto));
        AirlineDTO body = AirlineMapper.toDTO(saved);
        return ResponseEntity.created(URI.create("/api/airlines/" + saved.getId())).body(body);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AirlineDTO> get(@PathVariable Long id) {
        return airlineRepository.findById(id)
                .map(AirlineMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
