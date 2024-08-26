package com.idr.metro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.idr.metro.entity.Station;
import com.idr.metro.service.StationService;

@RestController
public class StationInfoController {

	@Autowired
	StationService stationService;

	@GetMapping("/getstation")
	public List<Station> getall() {
		return stationService.getall();

	}

}
