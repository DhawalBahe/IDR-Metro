package com.idr.metro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.idr.metro.entity.Booking;
import com.idr.metro.service.BookingService;

@RestController
public class BookingController {

	@Autowired
	BookingService bookingService;

	@PostMapping("/getticket")
	public Booking getTicket(@RequestBody Booking booking) {
		return bookingService.getTicket(booking);
	}

	@PostMapping("/payment")
	public String payment(Booking booking) {
		return bookingService.payment(booking);

	}
}
