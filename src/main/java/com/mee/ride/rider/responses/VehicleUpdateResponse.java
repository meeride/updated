package com.mee.ride.rider.responses;

import com.mee.ride.rider.model.Vehicles;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleUpdateResponse {
	private String status;
	private String message;
	private Vehicles vehicle;
}
