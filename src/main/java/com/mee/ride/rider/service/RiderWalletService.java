package com.mee.ride.rider.service;

import java.util.List;

import com.mee.ride.rider.model.RiderWallet;

public interface RiderWalletService {

	RiderWallet addToWallet(RiderWallet riderWallet);
	List<RiderWallet> getWalletData();
	
}
