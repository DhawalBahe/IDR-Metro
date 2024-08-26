package com.idr.metro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.idr.metro.entity.Station;

@Repository
public interface StationRepository extends JpaRepository<Station, Long> {

	Station findStationIdByStationName(String sourceStation);
}
