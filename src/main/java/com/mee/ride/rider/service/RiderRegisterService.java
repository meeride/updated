package com.mee.ride.rider.service;

import com.mee.ride.rider.dto.RegisterDto;
import com.mee.ride.rider.model.RegisterRider;
import java.util.List;

public interface RiderRegisterService {
	
	RegisterRider registerRider(RegisterRider rider);
	RegisterRider updateRiderToken(RegisterRider rider);
	RegisterRider approveRider(RegisterRider rider);
	RegisterRider updateRider(RegisterRider rider);
	List<RegisterDto> getAllRiders();
	void deleteRider(RegisterRider rider);
	
}
