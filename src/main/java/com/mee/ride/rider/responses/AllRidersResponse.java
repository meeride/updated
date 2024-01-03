package com.mee.ride.rider.responses;

import java.util.List;

import com.mee.ride.rider.dto.RegisterDto;

import lombok.Data;

@Data
public class AllRidersResponse {
	private String status;
	private String message;
	private List<RegisterDto> list;

}
