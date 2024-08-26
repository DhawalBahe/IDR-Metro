package com.idr.metro.serviceimppl;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idr.metro.entity.Booking;
import com.idr.metro.entity.Station;
import com.idr.metro.repository.BookingRepository;
import com.idr.metro.repository.StationRepository;
import com.idr.metro.service.BookingService;

@Service
public class BookingServiceImpl implements BookingService {

	@Autowired
	BookingRepository bookingRepository;

	@Autowired
	StationRepository stationRepository;

	@Override
	public Booking getTicket(Booking booking) {
		Station sourceStation = stationRepository.findStationIdByStationName(booking.getSourceStation());
		Station destinationStation = stationRepository.findStationIdByStationName(booking.getDestinationStation());
		if (sourceStation != null && destinationStation != null) {
			double distance = destinationStation.getDistance() - sourceStation.getDistance();
			double cost = calculatePrice(distance, booking);
			booking.setCost(cost);
			return bookingRepository.save(booking);
		} else {
			System.out.println("Source or destination station not found");
			return null;
		}
	}

	@Override
	public String payment(Booking booking) {
		double cst = booking.getCost();
		boolean flag = true;
		if (flag) {
			System.out.println("your payment of rupees" + cst + "is Done");
			booking.setStatus("Confirmed");
			for (int i = 0; i <= booking.getNoOfPassengers() - 1; i++) {
				String randomBarcode = generateRandomBarcode();
				booking.setBarCode(randomBarcode);
			}
			return "confirm";
		} else {
			System.out.println("your payment of rupees" + cst + "is not Done");
			booking.setStatus("Cancellled");
			return "Cancelled";
		}
	}

	private String generateRandomBarcode() {
		String allowedCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		int barcodeLength = 10; // Adjust the length as needed
		StringBuilder randomBarcode = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < barcodeLength; i++) {
			int randomIndex = random.nextInt(allowedCharacters.length());
			char randomChar = allowedCharacters.charAt(randomIndex);
			randomBarcode.append(randomChar);
		}
		return randomBarcode.toString();
	}

	private double calculatePrice(double distance, Booking booking) {
		double[] distanceRanges = { 0.0, 2.0, 5.0, 8.0, 10.0, 12.0 };
		double[] costs = { 20, 40, 60, 80, 100 };

		for (int i = 0; i < distanceRanges.length - 1; i++) {
			if (distance >= distanceRanges[i] && distance < distanceRanges[i + 1]) {
				return costs[i] * booking.getNoOfPassengers();
			}
		}
		return 0;
	}

}
