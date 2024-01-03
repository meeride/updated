package com.mee.ride.rider.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mee.ride.rider.model.RiderWallet;
import com.mee.ride.rider.repository.WalletRepository;
import com.mee.ride.rider.service.RiderWalletService;

@Service
public class RiderWalletServiceImpl implements RiderWalletService {
	
	private WalletRepository wRepository;
	
	

	public RiderWalletServiceImpl(WalletRepository wRepository) {
		super();
		this.wRepository = wRepository;
	}

	@Override
	public RiderWallet addToWallet(RiderWallet riderWallet) {
		// TODO Auto-generated method stub
		return wRepository.save(riderWallet);
	}

	@Override
	public List<RiderWallet> getWalletData() {
		// TODO Auto-generated method stub
		return null;
	}

}
