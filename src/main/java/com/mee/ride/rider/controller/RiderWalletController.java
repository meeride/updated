package com.mee.ride.rider.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mee.ride.rider.model.RegisterRider;
import com.mee.ride.rider.model.RiderWallet;
import com.mee.ride.rider.repository.RiderRegisterRepository;
import com.mee.ride.rider.repository.WalletRepository;
import com.mee.ride.rider.responses.CommonResponse;
import com.mee.ride.rider.responses.FirebaseTokenUpdateResponse;
import com.mee.ride.rider.responses.WalletHistoryResponse;
import com.mee.ride.rider.responses.WalletResponse;
import com.mee.ride.rider.service.RiderWalletService;

@RestController
@RequestMapping("/api/rider/wallet")
public class RiderWalletController {

	Logger logger = LoggerFactory.getLogger(RiderWalletController.class);
	private ModelMapper modelMapper;

	WalletRepository wRepository;
	RiderWalletService wService;
	RiderRegisterRepository riderRegisterRepository;

	public RiderWalletController(WalletRepository wRepository, RiderWalletService wService,
			RiderRegisterRepository riderRegisterRepository) {
		super();
		this.wRepository = wRepository;
		this.wService = wService;
		this.riderRegisterRepository = riderRegisterRepository;
	}

	// GET RIDER WALLET AMOUNT
	@PostMapping("/get_wallet_amount")
	public ResponseEntity<WalletResponse> getWalletAmount(@RequestParam("rider_id") String rider_id) {
		RegisterRider e_rider = riderRegisterRepository.findByRiderId(rider_id);
		logger.info("WALLET AMOUNT RIDER_ID : " + rider_id);
		WalletResponse response = new WalletResponse();

		if (e_rider == null) {

			response.setStatus("0");
			response.setMessage("Invalid Rider Id");
			response.setTotal_amount(0.0);
			response.setCredit_amount(0.0);
			response.setDebit_amount(0.0);
			response.setBalance_amount(0.0);
			return new ResponseEntity<WalletResponse>(response, HttpStatus.OK);
		}

		List<RiderWallet> c_walletList = wRepository.findAllByRiderIdAndType(rider_id, "CREDIT");
		List<RiderWallet> d_walletList = wRepository.findAllByRiderIdAndType(rider_id, "DEBIT");
		Double c_amount = 0.0;
		Double d_amount = 0.0;
		

		if (c_walletList != null) {
			for (int i = 0; i < c_walletList.size(); i++) {
				c_amount = c_amount + c_walletList.get(i).getAmount();
			}
		}

		if (d_walletList != null) {
			for (int i = 0; i < d_walletList.size(); i++) {
				d_amount = d_amount + d_walletList.get(i).getAmount();
			}
		}

		logger.info("CREDIT WALLET AMOUNT : " + c_amount);
		logger.info("DEBIT WALLET AMOUNT : " + d_amount);
		//Double total_amount = c_amount + d_amount;
		Double balance_amount = c_amount - d_amount;
		response.setTotal_amount(c_amount);
		response.setCredit_amount(c_amount);
		response.setDebit_amount(d_amount);
		response.setBalance_amount(balance_amount);
		response.setStatus("1");
		response.setMessage("Wallet Amount");

		return new ResponseEntity<WalletResponse>(response, HttpStatus.OK);
	}

	// WITHDRAW FROM WALLET
	@PostMapping("/wallet_amount_withdraw")
	public ResponseEntity<CommonResponse> walletAmountWithdraw(@RequestParam("rider_id") String rider_id,
			@RequestParam("amount") String amount, @RequestParam("transaction_id") String transaction_id) {

		logger.info("WALLET AMOUNT WITHDRAW RIDER_ID : " + rider_id);
		logger.info("WALLET AMOUNT WITHDRAW AMOUNT : " + amount);
		logger.info("WALLET AMOUNT WITHDRAW TRANSACTION ID : " + transaction_id);

		RegisterRider e_rider = riderRegisterRepository.findByRiderId(rider_id);

		CommonResponse response = new CommonResponse();

		if (e_rider == null) {

			response.setStatus("0");
			response.setMessage("Invalid Rider Id");
			return new ResponseEntity<CommonResponse>(response, HttpStatus.OK);
		}

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();
		String current_date = formatter.format(date);
		RiderWallet wallet = new RiderWallet();
		wallet.setRiderId(rider_id);
		wallet.setType("DEBIT");
		wallet.setTimeStamp(current_date);
		wallet.setAmount(Double.parseDouble(amount));
		wallet.setTransactionId(transaction_id);
		
		wService.addToWallet(wallet);

		response.setStatus("1");
		response.setMessage("Wallet Amount Withdrawn");

		return new ResponseEntity<CommonResponse>(response, HttpStatus.OK);
	}
	
	// GET RIDER WALLET HISTORY
		@PostMapping("/get_wallet_history")
		public ResponseEntity<WalletHistoryResponse> getWalletHistory(@RequestParam("rider_id") String rider_id) {
			
			RegisterRider e_rider = riderRegisterRepository.findByRiderId(rider_id);
			logger.info("WALLET HISTORY RIDER_ID : " + rider_id);
			WalletHistoryResponse response = new WalletHistoryResponse();

			if (e_rider == null) {

				response.setStatus("0");
				response.setMessage("Invalid Rider Id");
				return new ResponseEntity<WalletHistoryResponse>(response, HttpStatus.OK);
			}

			List<RiderWallet> list = wRepository.findAllByRiderId(rider_id);
		
			response.setStatus("1");
			response.setMessage("Wallet Amount");
			response.setList(list);

			return new ResponseEntity<WalletHistoryResponse>(response, HttpStatus.OK);
		}
	

}
