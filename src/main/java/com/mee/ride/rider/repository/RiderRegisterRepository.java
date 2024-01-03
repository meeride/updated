package com.mee.ride.rider.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mee.ride.rider.model.RegisterRider;


public interface RiderRegisterRepository extends JpaRepository<RegisterRider, Long>{

	RegisterRider findByEmail(String email);
	RegisterRider findByMobile(String mobile);
	RegisterRider findByRiderId(String rider_id);
	RegisterRider findByEmailAndPassword(String email, String password);
	RegisterRider findByMobileAndPassword(String mobile, String password);

}
