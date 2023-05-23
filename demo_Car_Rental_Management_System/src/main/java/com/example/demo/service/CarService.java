package com.example.demo.service;


import java.time.LocalDate;


import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.entity.Car;
import com.example.demo.entity.Rental;
import com.example.demo.persistence.CarRepository;
import com.example.demo.persistence.RentalRepository;


@Service
public class CarService {
    @Autowired
    private CarRepository carRepository;
    
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }
    
    
    @Autowired
    private RentalRepository rentalRepository;
    
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public Car getCarById(Long id) {
        return carRepository.findById(id).orElse(null);
    }

    public Car addCar(Car car) {
        return carRepository.save(car);
    }
    
    public Car updateCar(Car car) {
        return carRepository.save(car);
    }
    
    public void deleteCarById(Long id) {
        carRepository.deleteById(id);
    }

    public List<Car> searchCars(LocalDate startDate, LocalDate endDate) {
        List<Rental> rentals = rentalRepository.findByStartDateLessThanEqualAndEndDateGreaterThanEqual(startDate, endDate);
        List<String> rentedCarLicenseNumbers = rentals.stream().map(rental -> rental.getCar().getCarLicenseNumber()).collect(Collectors.toList());
        List<Car> availableCars = carRepository.findAll();
        availableCars = availableCars.stream()
                .filter(car -> !rentedCarLicenseNumbers.contains(car.getCarLicenseNumber()))
                .collect(Collectors.toList());
        return availableCars;
    }


    public double calculatePrice(LocalDate startDate, LocalDate endDate, String carLicenseNumber) {
        Car car = carRepository.findByCarLicenseNumber(carLicenseNumber);
        long days = ChronoUnit.DAYS.between(startDate, endDate);
        double total = car.getBasePrice() * days;
        double hours = ChronoUnit.HOURS.between(startDate.atStartOfDay(), endDate.atStartOfDay());
        total += car.getPph() * hours;
        return total;
    }
}

