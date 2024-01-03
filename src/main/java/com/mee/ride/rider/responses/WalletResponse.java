package com.mee.ride.rider.responses;

import java.util.List;

import com.mee.ride.rider.model.RiderWallet;

import lombok.Data;

@Data
public class WalletResponse {
	private String status;
	private String message;
	private Double total_amount;
	private Double credit_amount;
	private Double debit_amount;
	private Double balance_amount;
}
