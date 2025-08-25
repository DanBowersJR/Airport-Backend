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

import java.util.List;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedDatabase(CityRepository cityRepo,
                                   AirportRepository airportRepo,
                                   AircraftRepository aircraftRepo,
                                   PassengerRepository passengerRepo) {
        return args -> {
            // --- Seed Cities (safe: get first if duplicates exist) ---
            City toronto = cityRepo.findByName("Toronto")
                    .stream().findFirst()
                    .orElseGet(() -> cityRepo.save(new City("Toronto", "Ontario", 3000000)));

            City newYork = cityRepo.findByName("New York")
                    .stream().findFirst()
                    .orElseGet(() -> cityRepo.save(new City("New York", "New York", 8400000)));

            City london = cityRepo.findByName("London")
                    .stream().findFirst()
                    .orElseGet(() -> cityRepo.save(new City("London", "England", 8900000)));

            // --- Seed Airports ---
            Airport pearson = airportRepo.findByCode("YYZ").orElseGet(() -> {
                Airport a = new Airport();
                a.setCode("YYZ");
                a.setName("Toronto Pearson");
                a.setCity(toronto);
                return airportRepo.save(a);
            });

            Airport billyBishop = airportRepo.findByCode("YTZ").orElseGet(() -> {
                Airport a = new Airport();
                a.setCode("YTZ");
                a.setName("Billy Bishop Toronto City Airport");
                a.setCity(toronto);
                return airportRepo.save(a);
            });

            Airport jfk = airportRepo.findByCode("JFK").orElseGet(() -> {
                Airport a = new Airport();
                a.setCode("JFK");
                a.setName("John F. Kennedy");
                a.setCity(newYork);
                return airportRepo.save(a);
            });

            Airport heathrow = airportRepo.findByCode("LHR").orElseGet(() -> {
                Airport a = new Airport();
                a.setCode("LHR");
                a.setName("Heathrow");
                a.setCity(london);
                return airportRepo.save(a);
            });

            // --- Seed Aircraft ---
            Aircraft boeing777 = aircraftRepo.findAll().stream()
                    .filter(a -> a.getType().equals("Boeing 777"))
                    .findFirst()
                    .orElseGet(() -> aircraftRepo.save(new Aircraft("Boeing 777", "Air Canada", 300)));

            Aircraft airbusA350 = aircraftRepo.findAll().stream()
                    .filter(a -> a.getType().equals("Airbus A350"))
                    .findFirst()
                    .orElseGet(() -> aircraftRepo.save(new Aircraft("Airbus A350", "Delta Airlines", 280)));

            Aircraft boeing787 = aircraftRepo.findAll().stream()
                    .filter(a -> a.getType().equals("Boeing 787"))
                    .findFirst()
                    .orElseGet(() -> aircraftRepo.save(new Aircraft("Boeing 787", "British Airways", 250)));

            // ✅ Link Aircraft ↔ Airports
            boeing777.setAirports(List.of(pearson, jfk));
            airbusA350.setAirports(List.of(jfk, heathrow));
            boeing787.setAirports(List.of(pearson, heathrow));
            aircraftRepo.saveAll(List.of(boeing777, airbusA350, boeing787));

            // --- Seed Passengers ---
            Passenger alice = passengerRepo.findByFirstNameAndLastName("Alice", "Smith")
                    .orElseGet(() -> passengerRepo.save(new Passenger("Alice", "Smith", "111-111-1111", toronto)));

            Passenger bob = passengerRepo.findByFirstNameAndLastName("Bob", "Johnson")
                    .orElseGet(() -> passengerRepo.save(new Passenger("Bob", "Johnson", "222-222-2222", newYork)));

            Passenger charlie = passengerRepo.findByFirstNameAndLastName("Charlie", "Brown")
                    .orElseGet(() -> passengerRepo.save(new Passenger("Charlie", "Brown", "333-333-3333", london)));

            Passenger diana = passengerRepo.findByFirstNameAndLastName("Diana", "White")
                    .orElseGet(() -> passengerRepo.save(new Passenger("Diana", "White", "444-444-4444", toronto)));

            // ✅ Link Passengers ↔ Aircraft
            alice.setAircraftList(List.of(boeing777, airbusA350));
            bob.setAircraftList(List.of(airbusA350));
            charlie.setAircraftList(List.of(boeing787));
            diana.setAircraftList(List.of(boeing777));

            passengerRepo.saveAll(List.of(alice, bob, charlie, diana));
        };
    }
}
