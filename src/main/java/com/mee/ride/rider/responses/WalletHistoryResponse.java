package com.mee.ride.rider.responses;

import java.util.List;

import com.mee.ride.rider.model.RiderWallet;

import lombok.Data;

@Data
public class WalletHistoryResponse {

	private String status;
	private String message;
	private List<RiderWallet> list;
}
