package com.keyin.api.service;

import com.keyin.api.dto.PassengerDTO;
import com.keyin.api.dto.AircraftDTO;
import com.keyin.api.dto.AirportDTO;
import com.keyin.api.mapper.PassengerMapper;
import com.keyin.api.mapper.AircraftMapper;
import com.keyin.api.mapper.AirportMapper;
import com.keyin.api.model.Passenger;
import com.keyin.api.model.City;
import com.keyin.api.model.Aircraft;
import com.keyin.api.model.Airport;
import com.keyin.api.repository.PassengerRepository;
import com.keyin.api.repository.CityRepository;
import com.keyin.api.repository.AircraftRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PassengerService {

    private final PassengerRepository passengerRepository;
    private final CityRepository cityRepository;
    private final AircraftRepository aircraftRepository;

    public PassengerService(PassengerRepository passengerRepository,
                            CityRepository cityRepository,
                            AircraftRepository aircraftRepository) {
        this.passengerRepository = passengerRepository;
        this.cityRepository = cityRepository;
        this.aircraftRepository = aircraftRepository;
    }

    // ✅ Get all passengers
    public List<PassengerDTO> getAllPassengers() {
        return passengerRepository.findAll()
                .stream()
                .map(PassengerMapper::toDTO)
                .collect(Collectors.toList());
    }

    // ✅ Get passenger by ID
    public PassengerDTO getPassengerById(Long id) {
        Passenger passenger = passengerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Passenger not found with id: " + id));
        return PassengerMapper.toDTO(passenger);
    }

    // ✅ Save new passenger
    public PassengerDTO savePassenger(PassengerDTO dto) {
        City city = null;
        if (dto.getCityId() != null) {
            city = cityRepository.findById(dto.getCityId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "City not found with id: " + dto.getCityId()));
        }

        List<Aircraft> aircraftList = (dto.getAircraftIds() != null && !dto.getAircraftIds().isEmpty())
                ? aircraftRepository.findAllById(dto.getAircraftIds())
                : List.of();

        Passenger passenger = PassengerMapper.toEntity(dto, city, aircraftList);
        Passenger saved = passengerRepository.save(passenger);

        return PassengerMapper.toDTO(saved);
    }

    // ✅ Update passenger
    public PassengerDTO updatePassenger(Long id, PassengerDTO dto) {
        Passenger existing = passengerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Passenger not found with id: " + id));

        City city = null;
        if (dto.getCityId() != null) {
            city = cityRepository.findById(dto.getCityId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "City not found with id: " + dto.getCityId()));
        }

        List<Aircraft> aircraftList = (dto.getAircraftIds() != null && !dto.getAircraftIds().isEmpty())
                ? aircraftRepository.findAllById(dto.getAircraftIds())
                : existing.getAircraftList();

        existing.setFirstName(dto.getFirstName());
        existing.setLastName(dto.getLastName());
        existing.setPhoneNumber(dto.getPhoneNumber());
        existing.setCity(city);
        existing.setAircraftList(aircraftList);

        Passenger updated = passengerRepository.save(existing);
        return PassengerMapper.toDTO(updated);
    }

    // ✅ Delete passenger
    public boolean deletePassenger(Long id) {
        if (!passengerRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Passenger not found with id: " + id);
        }
        passengerRepository.deleteById(id);
        return true;
    }

    // 🔎 Q2: What aircraft has each passenger flown on?
    public List<AircraftDTO> getAircraftByPassenger(Long passengerId) {
        Passenger passenger = passengerRepository.findById(passengerId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Passenger not found with id: " + passengerId));

        if (passenger.getAircraftList() == null || passenger.getAircraftList().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "No aircraft found for passenger with id: " + passengerId);
        }

        return passenger.getAircraftList()
                .stream()
                .map(AircraftMapper::toDTO)
                .collect(Collectors.toList());
    }

    // 🔎 Q4: What airports have passengers used?
    public Set<AirportDTO> getAirportsUsedByPassenger(Long passengerId) {
        Passenger passenger = passengerRepository.findById(passengerId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Passenger not found with id: " + passengerId));

        if (passenger.getAircraftList() == null || passenger.getAircraftList().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "No aircraft (and therefore no airports) found for passenger with id: " + passengerId);
        }

        return passenger.getAircraftList().stream()
                .flatMap(aircraft -> aircraft.getAirports().stream())
                .map(AirportMapper::toDTO)
                .collect(Collectors.toSet()); // ✅ ensures distinct airports
    }
}
