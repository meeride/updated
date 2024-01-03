package com.mee.ride.rider.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mee.ride.rider.model.Vehicles;

public interface VehiclesRepository extends JpaRepository<Vehicles, Long>{
	Vehicles findByVehicleId(String vehicle_id);
}
