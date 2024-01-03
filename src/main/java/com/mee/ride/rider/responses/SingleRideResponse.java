package com.mee.ride.rider.responses;

import com.mee.ride.rider.dto.AcceptedRidesDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SingleRideResponse {
	
	private String status;
	private String message;
	private AcceptedRidesDto rides;
    

}
