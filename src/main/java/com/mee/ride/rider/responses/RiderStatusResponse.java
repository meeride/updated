package com.mee.ride.rider.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RiderStatusResponse {
	public String status;
	public String message;
	public String rider_status;
	
}
