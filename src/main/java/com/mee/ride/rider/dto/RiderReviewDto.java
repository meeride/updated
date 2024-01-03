package com.mee.ride.rider.dto;

import com.mee.ride.rider.model.RiderReview;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class RiderReviewDto {
	
	private long ID;
	private String riderId;
	private String userId;
	private String orderId;
	private String stars;
	private String review;
	private String reviewDate;
	private String riderName;
	private String userName;
	private String userImage;

}
