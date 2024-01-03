package com.mee.ride.rider.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FirebaseTokenUpdateResponse {
	public String status;
	public String message;
}
