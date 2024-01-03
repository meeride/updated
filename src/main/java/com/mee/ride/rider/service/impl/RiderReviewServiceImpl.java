package com.mee.ride.rider.service.impl;

import org.springframework.stereotype.Service;

import com.mee.ride.rider.model.RiderReview;
import com.mee.ride.rider.repository.RiderReviewRepository;
import com.mee.ride.rider.service.RiderReviewService;

@Service
public class RiderReviewServiceImpl implements RiderReviewService{
	
	private RiderReviewRepository reviewRepository;
	
	

	public RiderReviewServiceImpl(RiderReviewRepository reviewRepository) {
		super();
		this.reviewRepository = reviewRepository;
	}



	@Override
	public RiderReview addReview(RiderReview review) {
		// TODO Auto-generated method stub
		return reviewRepository.save(review);
	}

}
