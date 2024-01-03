package com.mee.ride.rider.responses;
import lombok.Data;

@Data
public class RidesCountResponse {
	private String status;
	private String message;
	private String pending;
	private String accepted;
	private String completed;
	private String rejected;

}
