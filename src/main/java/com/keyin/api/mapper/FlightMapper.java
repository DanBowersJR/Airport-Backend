package com.keyin.api.mapper;

import com.keyin.api.dto.FlightDTO;
import com.keyin.api.model.*;

public class FlightMapper {

    // Entity -> DTO
    public static FlightDTO toDTO(Flight flight) {
        if (flight == null) return null;

        FlightDTO dto = new FlightDTO();
        dto.setId(flight.getId());
        dto.setFlightNumber(flight.getFlightNumber());
        dto.setType(flight.getType() != null ? flight.getType().name() : null);
        dto.setStatus(flight.getStatus() != null ? flight.getStatus().name() : null);
        dto.setScheduledTime(flight.getScheduledTime());
        dto.setActualTime(flight.getActualTime());
        dto.setOriginCode(flight.getOriginCode());
        dto.setDestinationCode(flight.getDestinationCode());

        dto.setAirportId(flight.getAirport() != null ? flight.getAirport().getId() : null);
        dto.setAirlineId(flight.getAirline() != null ? flight.getAirline().getId() : null);
        dto.setGateId(flight.getGate() != null ? flight.getGate().getId() : null);
        dto.setAircraftId(flight.getAircraft() != null ? flight.getAircraft().getId() : null);

        return dto;
    }

    // DTO -> Entity (without relationships wired yet)
    public static Flight toEntity(FlightDTO dto) {
        if (dto == null) return null;

        Flight flight = new Flight();
        flight.setId(dto.getId());
        flight.setFlightNumber(dto.getFlightNumber());

        if (dto.getType() != null) {
            flight.setType(FlightType.valueOf(dto.getType()));
        }

        if (dto.getStatus() != null) {
            flight.setStatus(FlightStatus.valueOf(dto.getStatus()));
        }

        flight.setScheduledTime(dto.getScheduledTime());
        flight.setActualTime(dto.getActualTime());
        flight.setOriginCode(dto.getOriginCode());
        flight.setDestinationCode(dto.getDestinationCode());

        // ⚠️ Relationships (Airport, Airline, Gate, Aircraft)
        // will need to be resolved via repositories in the Service layer,
        // since DTO only has IDs and not the full object.

        return flight;
    }
}
