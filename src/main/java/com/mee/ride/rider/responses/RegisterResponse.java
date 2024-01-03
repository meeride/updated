package com.mee.ride.rider.responses;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
@Data
public class RegisterResponse {
	
	public String status;
	public String message;
	public String rider_id;
	

}
