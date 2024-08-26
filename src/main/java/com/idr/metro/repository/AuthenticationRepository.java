package com.idr.metro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.idr.metro.entity.User;

@Repository
public interface AuthenticationRepository extends JpaRepository<User, Long> {

	User findByUserName(String userName);

}
