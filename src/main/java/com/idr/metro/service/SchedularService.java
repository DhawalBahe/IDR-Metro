package com.idr.metro.service;

import java.util.List;

import com.idr.metro.entity.Schedule;

public interface SchedularService {

	List<Schedule> getlist();

	List<Schedule> getSchedulebyTime(String trainTimeing);

}
