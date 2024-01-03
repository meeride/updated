package com.mee.ride.user.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mee.ride.user.model.UserWallet;
import com.mee.ride.user.repository.UserWalletRepository;
import com.mee.ride.user.service.UserWalletService;

@Service
public class UserWalletServiceImpl implements UserWalletService {

	private UserWalletRepository wRepository;
	
	
	
	public UserWalletServiceImpl(UserWalletRepository wRepository) {
		super();
		this.wRepository = wRepository;
	}

	@Override
	public UserWallet addToWallet(UserWallet userWallet) {
		// TODO Auto-generated method stub
		return wRepository.save(userWallet);
	}

	@Override
	public List<UserWallet> getWalletData() {
		// TODO Auto-generated method stub
		return null;
	}

}
