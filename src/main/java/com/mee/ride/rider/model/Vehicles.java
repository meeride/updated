package com.mee.ride.rider.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Entity
@Table(name="vehicles_tbl")
@NoArgsConstructor
@Getter
@Setter
public class Vehicles {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long ID;
	
	@Column(name = "vehicle_id")
	private String vehicleId;
	
	@Column(name = "vehicle_name")
	private String vehicleName;
	
	@Column(name = "vehicle_image", length = 1000)
	private String vehicleImage;
	
	@Column(name = "price", length = 1000)
	private String price;
	
	@Column(name = "register_date")
	private String registerDate;
	
}
