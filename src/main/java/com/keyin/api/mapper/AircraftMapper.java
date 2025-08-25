package com.keyin.api.mapper;

import com.keyin.api.dto.AircraftDTO;
import com.keyin.api.model.Aircraft;
import com.keyin.api.model.Airport;
import com.keyin.api.model.Passenger;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class AircraftMapper {

    // ✅ Entity -> DTO
    public static AircraftDTO toDTO(Aircraft aircraft) {
        if (aircraft == null) return null;

        // Passengers -> IDs
        List<Long> passengerIds = (aircraft.getPassengers() != null)
                ? aircraft.getPassengers().stream()
                .map(Passenger::getId)
                .collect(Collectors.toList())
                : Collections.emptyList();

        // Airports -> IDs
        List<Long> airportIds = (aircraft.getAirports() != null)
                ? aircraft.getAirports().stream()
                .map(Airport::getId)
                .collect(Collectors.toList())
                : Collections.emptyList();

        // ✅ Match AircraftDTO constructor (airportIds first, then passengerIds)
        return new AircraftDTO(
                aircraft.getId(),
                aircraft.getType(),
                aircraft.getAirlineName(),
                aircraft.getNumberOfPassengers(),
                airportIds,
                passengerIds
        );
    }

    // ✅ DTO -> Entity
    public static Aircraft toEntity(AircraftDTO dto, List<Airport> airports, List<Passenger> passengers) {
        if (dto == null) return null;

        Aircraft aircraft = new Aircraft();
        aircraft.setId(dto.getId());
        aircraft.setType(dto.getType());
        aircraft.setAirlineName(dto.getAirlineName());
        aircraft.setNumberOfPassengers(dto.getNumberOfPassengers());

        // Safe assignments
        aircraft.setAirports(airports != null ? airports : Collections.emptyList());
        aircraft.setPassengers(passengers != null ? passengers : Collections.emptyList());

        return aircraft;
    }
}
