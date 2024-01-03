package com.mee.ride.user.responses;

import com.mee.ride.user.dto.UserLoginDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserOrderResponse {
	private String status;
	private String message;
	private String order_id;
}
