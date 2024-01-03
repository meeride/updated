package com.mee.ride.rider.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RegisterDto {

	private String riderId;
	private String name;
	private String email;
	private String mobile;
	private String address;
	private String vehicle_type_id;
	private String vehicle_name;
	private String vehicle_number;
	private String bank_name;
	private String account_number;
	private String ifsc;
	private String name_on_account;
	private String vehicle_image;
	private String rider_image;
	private String license_image;
	private String c_book_image;
	private String aadhar_image;
	private String approve_status;
	private String firebase_token;
	
}
