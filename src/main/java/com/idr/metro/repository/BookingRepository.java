package com.idr.metro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.idr.metro.entity.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

}
