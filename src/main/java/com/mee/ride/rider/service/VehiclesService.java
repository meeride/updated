package com.mee.ride.rider.service;

import java.util.List;

import com.mee.ride.rider.dto.RegisterDto;
import com.mee.ride.rider.model.RegisterRider;
import com.mee.ride.rider.model.Vehicles;

public interface VehiclesService {

	Vehicles registerVehicle(Vehicles vehicles);
	List<Vehicles> getAllVehicles();
	void deleteVehicle(Vehicles vehicles);
	Vehicles updateVehicle(Vehicles vehicles);
	
}
