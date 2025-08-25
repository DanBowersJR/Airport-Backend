package com.keyin.api.config;

import com.keyin.api.model.Aircraft;
import com.keyin.api.model.Airport;
import com.keyin.api.model.City;
import com.keyin.api.model.Passenger;
import com.keyin.api.repository.AircraftRepository;
import com.keyin.api.repository.AirportRepository;
import com.keyin.api.repository.CityRepository;
import com.keyin.api.repository.PassengerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedDatabase(CityRepository cityRepo,
                                   AirportRepository airportRepo,
                                   AircraftRepository aircraftRepo,
                                   PassengerRepository passengerRepo) {
        return args -> {
            // --- Seed Cities ---
            City toronto = cityRepo.findByName("Toronto")
                    .orElseGet(() -> cityRepo.save(new City("Toronto")));

            City newYork = cityRepo.findByName("New York")
                    .orElseGet(() -> cityRepo.save(new City("New York")));

            City london = cityRepo.findByName("London")
                    .orElseGet(() -> cityRepo.save(new City("London")));

            // --- Seed Airports ---
            if (airportRepo.findByCode("YYZ").isEmpty()) {
                Airport pearson = new Airport();
                pearson.setCode("YYZ");
                pearson.setName("Toronto Pearson");
                pearson.setCity(toronto);
                airportRepo.save(pearson);
            }

            if (airportRepo.findByCode("YTZ").isEmpty()) {
                Airport billyBishop = new Airport();
                billyBishop.setCode("YTZ");
                billyBishop.setName("Billy Bishop Toronto City Airport");
                billyBishop.setCity(toronto);
                airportRepo.save(billyBishop);
            }

            if (airportRepo.findByCode("JFK").isEmpty()) {
                Airport jfk = new Airport();
                jfk.setCode("JFK");
                jfk.setName("John F. Kennedy");
                jfk.setCity(newYork);
                airportRepo.save(jfk);
            }

            if (airportRepo.findByCode("LHR").isEmpty()) {
                Airport heathrow = new Airport();
                heathrow.setCode("LHR");
                heathrow.setName("Heathrow");
                heathrow.setCity(london);
                airportRepo.save(heathrow);
            }

            // --- Seed Aircraft ---
            if (aircraftRepo.findAll().isEmpty()) {
                aircraftRepo.save(new Aircraft("Air Canada", "Boeing 777", 300));
                aircraftRepo.save(new Aircraft("Delta Airlines", "Airbus A350", 280));
                aircraftRepo.save(new Aircraft("British Airways", "Boeing 787", 250));
            }

            // --- Seed Passengers ---
            if (passengerRepo.findAll().isEmpty()) {
                passengerRepo.save(new Passenger("Alice", "Smith", "111-111-1111", toronto));
                passengerRepo.save(new Passenger("Bob", "Johnson", "222-222-2222", newYork));
                passengerRepo.save(new Passenger("Charlie", "Brown", "333-333-3333", london));
                passengerRepo.save(new Passenger("Diana", "White", "444-444-4444", toronto));
            }
        };
    }
}
