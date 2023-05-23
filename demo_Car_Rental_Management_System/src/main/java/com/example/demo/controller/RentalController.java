package com.example.demo.controller;

import java.time.LocalDate;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.entity.Rental;
import com.example.demo.service.RentalService;


@RestController
public class RentalController {
    @Autowired
    private RentalService rentalService;

    @PostMapping("/car/book")
    public Rental bookCar(@RequestParam Long id,
                          @RequestParam String carLicenseNumber,
                          @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                          @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return rentalService.bookCar(id, carLicenseNumber, startDate, endDate);
    }

    @GetMapping("/car/bookings")
    public List<Rental> getCarBookings(@RequestParam String carLicenseNumber) {
        return rentalService.getCarBookings(carLicenseNumber);
    }
}