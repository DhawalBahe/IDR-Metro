package com.idr.metro.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.idr.metro.entity.Schedule;

@Repository
public interface SchedularRepository extends JpaRepository<Schedule, Long> {

	 @Query("SELECT s FROM Schedule s WHERE s.trainTimeing >= :givenTime")
	    List<Schedule> findAllAfterGivenTime(@Param("givenTime") String givenTime, Sort sort);
}
