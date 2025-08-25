package com.keyin.api.mapper;

import com.keyin.api.dto.AirportDTO;
import com.keyin.api.model.Airport;
import com.keyin.api.model.City;
import com.keyin.api.model.Aircraft;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class AirportMapper {

    // ✅ Entity -> DTO
    public static AirportDTO toDTO(Airport airport) {
        if (airport == null) return null;

        // Convert Aircraft -> IDs
        List<Long> aircraftIds = (airport.getAircraftList() != null)
                ? airport.getAircraftList().stream()
                .map(Aircraft::getId)
                .collect(Collectors.toList())
                : Collections.emptyList();

        return new AirportDTO(
                airport.getId(),
                airport.getName(),
                airport.getCode(),
                (airport.getCity() != null ? airport.getCity().getId() : null),
                aircraftIds
        );
    }

    // ✅ DTO -> Entity
    public static Airport toEntity(AirportDTO dto, City city, List<Aircraft> aircraftList) {
        if (dto == null) return null;

        Airport airport = new Airport();
        airport.setId(dto.getId());
        airport.setName(dto.getName());
        airport.setCode(dto.getCode());
        airport.setCity(city);
        airport.setAircraftList(aircraftList != null ? aircraftList : Collections.emptyList());

        return airport;
    }
}
