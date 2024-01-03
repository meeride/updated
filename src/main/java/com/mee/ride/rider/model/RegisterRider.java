package com.mee.ride.rider.model;

import java.util.Date;

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
@Table(name="rider_tbl")
@NoArgsConstructor
@Getter
@Setter
public class RegisterRider {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long ID;
	
	@Column(name = "rider_id")
	private String riderId;
	
	@Column(name = "name")
	private String name;
		
	@Column(name = "email")
	private String email;
	
	@Column(name = "mobile")
	private String mobile;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "address", length = 1000)
	private String address;
	
	@Column(name = "vehicle_type_id")
	private String vehicle_type_id;
	
	@Column(name = "vehicle_name")
	private String vehicle_name;
	
	@Column(name = "vehicle_number")
	private String vehicle_number;
	
	@Column(name = "bank_name", length = 1000)
	private String bank_name;
	
	@Column(name = "account_number", length = 1000)
	private String account_number;
	
	@Column(name = "ifsc", length = 1000)
	private String ifsc;
	
	@Column(name = "name_on_account", length = 1000)
	private String name_on_account;
	
	@Column(name = "vehicle_image", length = 1000)
	private String vehicle_image;
	
	@Column(name = "rider_image", length = 1000)
	private String rider_image;
	
	@Column(name = "license_image", length = 1000)
	private String license_image;
	
	@Column(name = "c_book_image", length = 1000)
	private String c_book_image;
	
	@Column(name = "aadhar_image", length = 1000)
	private String aadhar_image;
	
	@Column(name = "firebase_token", length = 1000)
	private String firebase_token;
	
	@Column(name = "approve_status")
	private String approve_status;
	
	@Column(name = "comission")
	private String comission;
	
	@Column(name = "reject_reason", length = 1000)
	private String reject_reason;
	
	@Column(name = "rider_status")
	private String riderStatus;
	
	@Column(name = "register_date")
	private String register_date;
	
	
}
