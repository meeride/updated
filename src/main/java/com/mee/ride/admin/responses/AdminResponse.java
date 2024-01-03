package com.mee.ride.admin.responses;


import com.mee.ride.rider.responses.CommonResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminResponse {
	private String status;
	private String message;
	private String name;
}
