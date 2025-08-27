package com.keyin.api.service;

import com.keyin.api.dto.GateDTO;
import com.keyin.api.mapper.GateMapper;
import com.keyin.api.model.Airport;
import com.keyin.api.model.Gate;
import com.keyin.api.repository.AirportRepository;
import com.keyin.api.repository.GateRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GateService {

    private final GateRepository gateRepository;
    private final AirportRepository airportRepository;

    public GateService(GateRepository gateRepository, AirportRepository airportRepository) {
        this.gateRepository = gateRepository;
        this.airportRepository = airportRepository;
    }

    // ----- Queries -----

    public List<GateDTO> listAll() {
        return gateRepository.findAll().stream()
                .map(GateMapper::toDTO)
                .toList();
    }

    public List<GateDTO> listByAirport(Long airportId) {
        return gateRepository.findByAirport_Id(airportId).stream()
                .map(GateMapper::toDTO)
                .toList();
    }

    public GateDTO get(Long id) {
        return gateRepository.findById(id)
                .map(GateMapper::toDTO)
                .orElse(null);
    }

    // ----- Commands -----

    @Transactional
    public GateDTO create(GateDTO dto) {
        if (dto.getAirportId() == null) {
            throw new IllegalArgumentException("airportId is required for a gate");
        }

        Airport airport = airportRepository.findById(dto.getAirportId())
                .orElseThrow(() -> new IllegalArgumentException("Airport not found: " + dto.getAirportId()));

        // FIX: pass both dto and airport to the mapper
        Gate gate = GateMapper.toEntity(dto, airport);

        Gate saved = gateRepository.save(gate);
        return GateMapper.toDTO(saved);
    }

    @Transactional
    public boolean delete(Long id) {
        if (!gateRepository.existsById(id)) return false;
        gateRepository.deleteById(id);
        return true;
    }
}
