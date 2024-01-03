package com.mee.ride.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mee.ride.user.model.BannerModel;


public interface BannerRepository extends JpaRepository<BannerModel, Long> {
	//BannerModel findByID(String id);

}
