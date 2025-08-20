package com.keyin.api.service;

import com.keyin.api.model.Passenger;
import com.keyin.api.repository.PassengerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PassengerService {

    private final PassengerRepository passengerRepository;

    // constructor injection
    public PassengerService(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    // get all passengers
    public List<Passenger> getAllPassengers() {
        return passengerRepository.findAll();
    }

    // get passenger by id
    public Passenger getPassengerById(Long id) {
        return passengerRepository.findById(id).orElse(null);
    }

    // save passenger
    public Passenger savePassenger(Passenger passenger) {
        return passengerRepository.save(passenger);
    }

    // delete passenger
    public void deletePassenger(Long id) {
        passengerRepository.deleteById(id);
    }
}
