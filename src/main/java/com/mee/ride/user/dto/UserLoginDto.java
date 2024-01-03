package com.mee.ride.user.dto;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDto {
	
	private String userId;
	private String name;
	private String gender;
	private String email;
	private String mobile;
	private String password;
	private String referCode;
	private String parentCode;
	private String userImage;
	private String approveStatus;
	private Double wallet;

}
