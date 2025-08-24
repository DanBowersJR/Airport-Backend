package com.keyin.api.mapper;

import com.keyin.api.dto.AircraftDTO;
import com.keyin.api.model.Aircraft;
import com.keyin.api.model.Passenger;
import com.keyin.api.model.Airport;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class AircraftMapper {

    // Convert Entity -> DTO
    public static AircraftDTO toDTO(Aircraft aircraft) {
        if (aircraft == null) return null;

        List<Long> passengerIds = (aircraft.getPassengers() != null)
                ? aircraft.getPassengers().stream()
                .map(Passenger::getId)
                .collect(Collectors.toList())
                : Collections.emptyList();

        return new AircraftDTO(
                aircraft.getId(),
                aircraft.getType(),
                aircraft.getAirlineName(),
                aircraft.getNumberOfPassengers(),
                (aircraft.getAirport() != null ? aircraft.getAirport().getId() : null),
                passengerIds
        );
    }

    // Convert DTO -> Entity
    public static Aircraft toEntity(AircraftDTO dto, Airport airport, List<Passenger> passengers) {
        if (dto == null) return null;

        Aircraft aircraft = new Aircraft();
        aircraft.setId(dto.getId());
        aircraft.setType(dto.getType());
        aircraft.setAirlineName(dto.getAirlineName());
        aircraft.setNumberOfPassengers(dto.getNumberOfPassengers());
        aircraft.setAirport(airport);
        aircraft.setPassengers(passengers != null ? passengers : Collections.emptyList());

        return aircraft;
    }
}
