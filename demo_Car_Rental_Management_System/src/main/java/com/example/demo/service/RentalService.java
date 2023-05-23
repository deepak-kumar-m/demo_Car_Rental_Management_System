package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Car;
import com.example.demo.entity.Rental;
import com.example.demo.entity.User;
import com.example.demo.persistence.CarRepository;
import com.example.demo.persistence.RentalRepository;
import com.example.demo.persistence.UserRepository;

@Service
public class RentalService {
    @Autowired
    private RentalRepository rentalRepository;
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CarService carService;

    public Rental bookCar(Long id, String carLicenseNumber, LocalDate startDate, LocalDate endDate) {
        User user = userRepository.findByid(id);
        Car car = carRepository.findByCarLicenseNumber(carLicenseNumber);
        double totalPrice = carService.calculatePrice(startDate, endDate, carLicenseNumber);
        Rental rental = new Rental();
        rental.setUser(user);
        rental.setCar(car);
        rental.setStartDate(startDate);
        rental.setEndDate(endDate);
        rental.setPrice(totalPrice);
        return rentalRepository.save(rental);
    }

    public List<Rental> getCarBookings(String carLicenseNumber) {
        Car car = carRepository.findByCarLicenseNumber(carLicenseNumber);
        return rentalRepository.findByCar(car);
    }
}