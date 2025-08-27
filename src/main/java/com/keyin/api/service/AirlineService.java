package com.keyin.api.service;

import com.keyin.api.dto.AirlineDTO;
import com.keyin.api.mapper.AirlineMapper;
import com.keyin.api.model.Airline;
import com.keyin.api.repository.AirlineRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AirlineService {

    private final AirlineRepository airlineRepository;

    public AirlineService(AirlineRepository airlineRepository) {
        this.airlineRepository = airlineRepository;
    }

    // ----- Queries -----

    public List<AirlineDTO> list() {
        return airlineRepository.findAll().stream()
                .map(AirlineMapper::toDTO)
                .toList();
    }

    public AirlineDTO get(Long id) {
        return airlineRepository.findById(id)
                .map(AirlineMapper::toDTO)
                .orElse(null);
    }

    public AirlineDTO getByCode(String code) {
        if (code == null) return null;
        return airlineRepository.findByCode(code.trim().toUpperCase())
                .map(AirlineMapper::toDTO)
                .orElse(null);
    }

    // ----- Commands -----

    @Transactional
    public AirlineDTO create(AirlineDTO dto) {
        Airline toSave = AirlineMapper.toEntity(dto);
        Airline saved = airlineRepository.save(toSave);
        return AirlineMapper.toDTO(saved);
    }

    @Transactional
    public boolean delete(Long id) {
        if (!airlineRepository.existsById(id)) return false;
        airlineRepository.deleteById(id);
        return true;
    }

    // Optional: very basic update (code and name)
    @Transactional
    public AirlineDTO update(Long id, AirlineDTO dto) {
        return airlineRepository.findById(id)
                .map(existing -> {
                    if (dto.getCode() != null) existing.setCode(dto.getCode());
                    if (dto.getName() != null) existing.setName(dto.getName());
                    return AirlineMapper.toDTO(airlineRepository.save(existing));
                })
                .orElse(null);
    }
}
