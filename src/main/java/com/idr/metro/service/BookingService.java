package com.idr.metro.service;

import com.idr.metro.entity.Booking;

public interface BookingService {

	Booking getTicket(Booking booking);

	String payment(Booking booking);

}
