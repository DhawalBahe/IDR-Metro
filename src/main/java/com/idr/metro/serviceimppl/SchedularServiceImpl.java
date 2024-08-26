package com.idr.metro.serviceimppl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.idr.metro.entity.Schedule;
import com.idr.metro.repository.SchedularRepository;
import com.idr.metro.service.SchedularService;

@Service
public class SchedularServiceImpl implements SchedularService {

	@Autowired
	SchedularRepository schedularRepository;

	@Override
	public List<Schedule> getlist() {
		return schedularRepository.findAll();
	}

	@Override
	public List<Schedule> getSchedulebyTime(String trainTimeing) {
		if (isBetween8amAnd6pm(trainTimeing)) {
			Sort sort = Sort.by(Sort.Direction.ASC, "trainTimeing");
			return schedularRepository.findAllAfterGivenTime(trainTimeing, sort);
		} else {
			return Collections.emptyList();
		}
	}

	private boolean isBetween8amAnd6pm(String convertedTime) {
		try {
			SimpleDateFormat inputFormat = new SimpleDateFormat("HH:mm:ss");
			Date eightAm = inputFormat.parse("08:00:00");
			Date sixPm = inputFormat.parse("18:00:00");

			Date inputDate = inputFormat.parse(convertedTime);

			return !inputDate.before(eightAm) && !inputDate.after(sixPm);
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
	}
}
