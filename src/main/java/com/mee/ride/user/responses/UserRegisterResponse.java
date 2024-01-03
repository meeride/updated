package com.mee.ride.user.responses;

import com.mee.ride.rider.responses.RegisterResponse;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Data
public class UserRegisterResponse {
	

	private String status;
	private String message;
	private String userId;
}
