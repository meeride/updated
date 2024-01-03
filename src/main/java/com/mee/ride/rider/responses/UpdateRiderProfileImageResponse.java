package com.mee.ride.rider.responses;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Data
public class UpdateRiderProfileImageResponse {
	
	private String status;
	private String message;
	private String rider_image;

}
