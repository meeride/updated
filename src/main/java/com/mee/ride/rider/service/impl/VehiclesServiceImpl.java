package com.mee.ride.rider.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mee.ride.rider.model.Vehicles;
import com.mee.ride.rider.repository.RiderRegisterRepository;
import com.mee.ride.rider.repository.VehiclesRepository;
import com.mee.ride.rider.service.VehiclesService;

@Service
public class VehiclesServiceImpl implements VehiclesService {

	//CONSTRUCTOR BASE DEPENDENCY INJECTION
	private VehiclesRepository vRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	
	public VehiclesServiceImpl(VehiclesRepository vRepository) {
		super();
		this.vRepository = vRepository;
	}

	@Override
	public Vehicles registerVehicle(Vehicles vehicles) {
		return vRepository.save(vehicles);
	}

	@Override
	public void deleteVehicle(Vehicles vehicles) {
		vRepository.delete(vehicles);
		
	}

	@Override
	public List<Vehicles> getAllVehicles() {
		return vRepository.findAll();
	}

	@Override
	public Vehicles updateVehicle(Vehicles vehicles) {
		return vRepository.save(vehicles);
	}

}
