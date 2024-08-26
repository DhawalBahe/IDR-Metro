package com.idr.metro.serviceimppl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idr.metro.entity.Station;
import com.idr.metro.repository.StationRepository;
import com.idr.metro.service.StationService;

@Service
public class StationServiceImpl implements StationService {

	@Autowired
	StationRepository stationRepository;

	@Override
	public List<Station> getall() {
		return stationRepository.findAll();
	}

}
