package com.mee.ride.rider.responses;

import java.util.List;

import com.mee.ride.rider.dto.RiderReviewDto;

import lombok.Data;


@Data
public class RiderReviewResponse {
	private String status;
	private String message;
	private List<RiderReviewDto> list;
}
