package com.mee.ride.rider.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mee.ride.rider.model.RiderWallet;


public interface WalletRepository extends JpaRepository<RiderWallet, Long> {
	List<RiderWallet> findAllByRiderIdAndType(String riderId, String type);
	List<RiderWallet> findAllByRiderId(String riderId);

}
