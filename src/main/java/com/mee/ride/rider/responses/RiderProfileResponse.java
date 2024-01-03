package com.mee.ride.rider.responses;

import lombok.Data;

@Data
public class RiderProfileResponse {
	private String status;
	private String message;
	private int total_rides;
	private double total_amount;
	private double total_km;

}
