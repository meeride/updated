package com.mee.ride.user.responses;

import java.util.List;

import com.mee.ride.user.dto.UserLoginDto;
import com.mee.ride.user.model.BannerModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserListResponse {
	
	private String status;
	private String message;
	private List<UserLoginDto> list;

}
