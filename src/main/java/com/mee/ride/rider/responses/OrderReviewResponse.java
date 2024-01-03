package com.mee.ride.rider.responses;



import com.mee.ride.rider.dto.RiderReviewDto;

import lombok.Data;

@Data
public class OrderReviewResponse {
	private String status;
	private String message;
	private RiderReviewDto review;
}
