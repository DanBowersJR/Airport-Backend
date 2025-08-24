package com.keyin.api.mapper;

import com.keyin.api.dto.PassengerDTO;
import com.keyin.api.model.Passenger;
import com.keyin.api.model.City;
import com.keyin.api.model.Aircraft;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PassengerMapper {

    // ✅ Convert Entity -> DTO
    public static PassengerDTO toDTO(Passenger passenger) {
        if (passenger == null) return null;

        // Map aircrafts to list of IDs
        List<Long> aircraftIds = (passenger.getAircraftList() != null)
                ? passenger.getAircraftList().stream()
                .map(Aircraft::getId)
                .collect(Collectors.toList())
                : Collections.emptyList();

        return new PassengerDTO(
                passenger.getId(),
                passenger.getFirstName(),
                passenger.getLastName(),
                passenger.getPhoneNumber(),
                (passenger.getCity() != null ? passenger.getCity().getId() : null), // Only send cityId
                aircraftIds // Only send aircraft IDs
        );
    }

    // ✅ Convert DTO -> Entity
    public static Passenger toEntity(PassengerDTO dto, City city, List<Aircraft> aircraftList) {
        if (dto == null) return null;

        Passenger passenger = new Passenger();
        passenger.setId(dto.getId());
        passenger.setFirstName(dto.getFirstName());
        passenger.setLastName(dto.getLastName());
        passenger.setPhoneNumber(dto.getPhoneNumber());

        // Set relationships
        passenger.setCity(city);
        passenger.setAircraftList(aircraftList != null ? aircraftList : Collections.emptyList());

        return passenger;
    }
}
