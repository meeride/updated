package com.mee.ride.user.service;

import java.util.List;

import com.mee.ride.user.model.UserWallet;


public interface UserWalletService {
	UserWallet addToWallet(UserWallet userWallet);
	List<UserWallet> getWalletData();
}
