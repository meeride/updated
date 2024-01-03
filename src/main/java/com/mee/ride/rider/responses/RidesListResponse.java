package com.mee.ride.rider.responses;

import java.util.List;

import com.mee.ride.rider.dto.AcceptedRidesDto;

import lombok.Data;

@Data
public class RidesListResponse {

	private String status;
	private String message;
	private List<AcceptedRidesDto> rides;
	
}
