package com.mee.ride.user.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mee.ride.rider.dto.RegisterDto;
import com.mee.ride.rider.model.RegisterRider;
import com.mee.ride.user.dto.UserLoginDto;
import com.mee.ride.user.model.UserModel;
import com.mee.ride.user.repository.UserRepository;
import com.mee.ride.user.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	
	@Autowired
	private ModelMapper modelMapper;
	
	private UserRepository uRepository;
	
	
	
	public UserServiceImpl(UserRepository uRepository) {
		super();
		this.uRepository = uRepository;
	}



	@Override
	public UserModel registerUser(UserModel userModel) {
		return uRepository.save(userModel);
	}



	@Override
	public UserModel updateUser(UserModel userModel) {
		return uRepository.save(userModel);
	}



	@Override
	public void deleteUser(UserModel userModel) {
		uRepository.delete(userModel);
		
	}



	@Override
	public List<UserLoginDto> getAllUsers() {
		List<UserModel> list = uRepository.findAll();
		List<UserLoginDto> users = list.stream().map((uid) -> this.modelMapper.map(uid, UserLoginDto.class))
				.collect(Collectors.toList());
		return users;
	}

}
