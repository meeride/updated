package com.mee.ride.user.responses;

import com.mee.ride.rider.dto.LoginDto;
import com.mee.ride.rider.responses.LoginResponse;
import com.mee.ride.user.dto.UserLoginDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginResponse {
	private String status;
	private String message;
	private UserLoginDto user_data;

}
