package com.mee.ride.user.service;
import java.util.List;

import com.mee.ride.user.dto.UserLoginDto;
import com.mee.ride.user.model.UserModel;

public interface UserService {
	UserModel registerUser(UserModel userModel);
	UserModel updateUser(UserModel userModel);
	void deleteUser(UserModel userModel);
	List<UserLoginDto> getAllUsers();
}
