package com.keyin.api.mapper;

import com.keyin.api.dto.GateDTO;
import com.keyin.api.model.Airport;
import com.keyin.api.model.Gate;

public class GateMapper {

    public static GateDTO toDTO(Gate gate) {
        if (gate == null) return null;
        Long airportId = gate.getAirport() != null ? gate.getAirport().getId() : null;
        return new GateDTO(gate.getId(), gate.getCode(), airportId);
    }

    public static Gate toEntity(GateDTO dto, Airport airport) {
        if (dto == null) return null;
        Gate g = new Gate();
        g.setId(dto.getId());
        g.setCode(dto.getCode());
        g.setAirport(airport); // must be provided by caller (looked up by airportId)
        return g;
    }
}
