package com.keyin.api.mapper;

import com.keyin.api.dto.AirlineDTO;
import com.keyin.api.model.Airline;

public class AirlineMapper {

    public static AirlineDTO toDTO(Airline airline) {
        if (airline == null) return null;
        return new AirlineDTO(
                airline.getId(),
                airline.getCode(),
                airline.getName()
        );
    }

    public static Airline toEntity(AirlineDTO dto) {
        if (dto == null) return null;
        Airline airline = new Airline();
        airline.setId(dto.getId());
        airline.setCode(dto.getCode());
        airline.setName(dto.getName());
        return airline;
    }
}
