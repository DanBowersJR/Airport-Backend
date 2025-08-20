package com.keyin.api.service;

import com.keyin.api.model.Passenger;
import com.keyin.api.repository.PassengerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PassengerService {

    private final PassengerRepository passengerRepository;

    public PassengerService(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    // ✅ Get all passengers
    public List<Passenger> getAllPassengers() {
        return passengerRepository.findAll();
    }

    // ✅ Get passenger by ID (throw 404 if not found)
    public Passenger getPassengerById(Long id) {
        return passengerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Passenger not found"));
    }

    // ✅ Save a new passenger
    public Passenger savePassenger(Passenger passenger) {
        return passengerRepository.save(passenger);
    }

    // ✅ Update passenger (throw 404 if not found)
    public Passenger updatePassenger(Long id, Passenger updatedPassenger) {
        Passenger existingPassenger = passengerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Passenger not found"));

        existingPassenger.setFirstName(updatedPassenger.getFirstName());
        existingPassenger.setLastName(updatedPassenger.getLastName());
        existingPassenger.setPhoneNumber(updatedPassenger.getPhoneNumber());

        return passengerRepository.save(existingPassenger);
    }

    // ✅ Delete passenger (throw 404 if not found)
    public void deletePassenger(Long id) {
        if (!passengerRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Passenger not found");
        }
        passengerRepository.deleteById(id);
    }
}
