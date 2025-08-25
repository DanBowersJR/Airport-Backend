package com.keyin.api.mapper;

import com.keyin.api.dto.CityDTO;
import com.keyin.api.model.City;
import com.keyin.api.model.Airport;   // ✅ Added missing import

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CityMapper {

    // ✅ Entity -> DTO
    public static CityDTO toDTO(City city) {
        if (city == null) return null;

        List<Long> airportIds = (city.getAirports() != null && !city.getAirports().isEmpty())
                ? city.getAirports().stream()
                .map(Airport::getId)  // ✅ works now
                .collect(Collectors.toList())
                : Collections.emptyList();

        return new CityDTO(
                city.getId(),
                city.getName(),
                city.getState(),
                city.getPopulation(),
                airportIds
        );
    }

    // ✅ DTO -> Entity
    public static City toEntity(CityDTO dto, List<Airport> airports) {
        if (dto == null) return null;

        City city = new City();
        city.setId(dto.getId());
        city.setName(dto.getName());
        city.setState(dto.getState());
        city.setPopulation(dto.getPopulation());

        if (airports != null && !airports.isEmpty()) {
            city.setAirports(airports);
            // ✅ keep both sides in sync
            airports.forEach(a -> a.setCity(city));
        } else {
            city.setAirports(Collections.emptyList());
        }

        return city;
    }
}
