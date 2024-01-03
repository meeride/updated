package com.mee.ride.rider.responses;

import com.mee.ride.rider.dto.LoginDto;
import com.mee.ride.rider.dto.RegisterDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
	public String status;
	public String message;
	public RegisterDto rider_data;
}
