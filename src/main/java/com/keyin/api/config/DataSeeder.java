package com.keyin.api.config;

import com.keyin.api.model.*;
import com.keyin.api.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedDatabase(CityRepository cityRepo,
                                   AirportRepository airportRepo,
                                   AircraftRepository aircraftRepo,
                                   PassengerRepository passengerRepo,
                                   AirlineRepository airlineRepo,
                                   GateRepository gateRepo,
                                   FlightRepository flightRepo) {

        return args -> {
            // ------- Cities -------
            City toronto = cityRepo.findByName("Toronto").stream().findFirst()
                    .orElseGet(() -> cityRepo.save(new City("Toronto", "Ontario", 3_000_000)));
            City newYork = cityRepo.findByName("New York").stream().findFirst()
                    .orElseGet(() -> cityRepo.save(new City("New York", "New York", 8_400_000)));
            City london  = cityRepo.findByName("London").stream().findFirst()
                    .orElseGet(() -> cityRepo.save(new City("London", "England", 8_900_000)));

            // ------- Airports -------
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

            // ------- Airlines -------
            Airline ac = airlineRepo.findAll().stream()
                    .filter(al -> "AC".equalsIgnoreCase(al.getCode()))
                    .findFirst()
                    .orElseGet(() -> airlineRepo.save(new Airline("AC", "Air Canada")));

            Airline dl = airlineRepo.findAll().stream()
                    .filter(al -> "DL".equalsIgnoreCase(al.getCode()))
                    .findFirst()
                    .orElseGet(() -> airlineRepo.save(new Airline("DL", "Delta Air Lines")));

            // ------- Gates (lookup by airport then by code) -------
            Gate yyzA1 = findOrCreateGate(gateRepo, pearson, "A1");
            Gate yyzA2 = findOrCreateGate(gateRepo, pearson, "A2");
            Gate jfkB1 = findOrCreateGate(gateRepo, jfk, "B1");

            // ------- Aircraft -------
            Aircraft boeing777 = aircraftRepo.findAll().stream()
                    .filter(a -> "Boeing 777".equals(a.getType()))
                    .findFirst()
                    .orElseGet(() -> aircraftRepo.save(new Aircraft("Boeing 777", "Air Canada", 300)));

            Aircraft airbusA350 = aircraftRepo.findAll().stream()
                    .filter(a -> "Airbus A350".equals(a.getType()))
                    .findFirst()
                    .orElseGet(() -> aircraftRepo.save(new Aircraft("Airbus A350", "Delta Airlines", 280)));

            Aircraft boeing787 = aircraftRepo.findAll().stream()
                    .filter(a -> "Boeing 787".equals(a.getType()))
                    .findFirst()
                    .orElseGet(() -> aircraftRepo.save(new Aircraft("Boeing 787", "British Airways", 250)));

            // Link Aircraft ↔ Airports
            boeing777.setAirports(List.of(pearson, jfk));
            airbusA350.setAirports(List.of(jfk, heathrow));
            boeing787.setAirports(List.of(pearson, heathrow));
            aircraftRepo.saveAll(List.of(boeing777, airbusA350, boeing787));

            // ------- Passengers -------
            Passenger alice = passengerRepo.findByFirstNameAndLastName("Alice", "Smith")
                    .orElseGet(() -> passengerRepo.save(new Passenger("Alice", "Smith", "111-111-1111", toronto)));
            Passenger bob = passengerRepo.findByFirstNameAndLastName("Bob", "Johnson")
                    .orElseGet(() -> passengerRepo.save(new Passenger("Bob", "Johnson", "222-222-2222", newYork)));
            Passenger charlie = passengerRepo.findByFirstNameAndLastName("Charlie", "Brown")
                    .orElseGet(() -> passengerRepo.save(new Passenger("Charlie", "Brown", "333-333-3333", london)));
            Passenger diana = passengerRepo.findByFirstNameAndLastName("Diana", "White")
                    .orElseGet(() -> passengerRepo.save(new Passenger("Diana", "White", "444-444-4444", toronto)));

            // Link Passengers ↔ Aircraft
            alice.setAircraftList(List.of(boeing777, airbusA350));
            bob.setAircraftList(List.of(airbusA350));
            charlie.setAircraftList(List.of(boeing787));
            diana.setAircraftList(List.of(boeing777));
            passengerRepo.saveAll(List.of(alice, bob, charlie, diana));

            // ------- Flights (seed only if table is empty) -------
            if (flightRepo.count() == 0) {
                // ARRIVAL to YYZ from JFK
                Flight f1 = new Flight();
                f1.setFlightNumber("AC101");
                f1.setType(FlightType.ARRIVAL);
                f1.setStatus(FlightStatus.SCHEDULED);
                f1.setScheduledTime(LocalDateTime.now().plusHours(1));
                f1.setOriginCode("JFK");
                f1.setDestinationCode("YYZ");
                f1.setAirport(pearson);
                f1.setAirline(ac);
                f1.setGate(yyzA1);
                f1.setAircraft(boeing777);

                // DEPARTURE from YYZ to LHR
                Flight f2 = new Flight();
                f2.setFlightNumber("DL200");
                f2.setType(FlightType.DEPARTURE);
                f2.setStatus(FlightStatus.SCHEDULED);
                f2.setScheduledTime(LocalDateTime.now().plusHours(2));
                f2.setOriginCode("YYZ");
                f2.setDestinationCode("LHR");
                f2.setAirport(pearson);
                f2.setAirline(dl);
                f2.setGate(yyzA2);
                f2.setAircraft(airbusA350);

                flightRepo.saveAll(List.of(f1, f2));
            }
        };
    }

    private Gate findOrCreateGate(GateRepository gateRepo, Airport airport, String code) {
        List<Gate> existing = gateRepo.findByAirport_Id(airport.getId());
        Optional<Gate> match = existing.stream()
                .filter(g -> code.equalsIgnoreCase(g.getCode()))
                .findFirst();
        if (match.isPresent()) return match.get();

        Gate g = new Gate();
        g.setCode(code);
        g.setAirport(airport);
        return gateRepo.save(g);
    }
}
