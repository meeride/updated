package com.mee.ride.rider.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mee.ride.rider.model.RiderReview;

public interface RiderReviewRepository extends JpaRepository<RiderReview, Long>{
	List<RiderReview> findAllByRiderId(String riderId);
	RiderReview findByOrderId(String orderId);

}
