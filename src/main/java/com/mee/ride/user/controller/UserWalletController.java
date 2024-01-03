package com.mee.ride.user.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.mee.ride.rider.responses.CommonResponse;
import com.mee.ride.rider.responses.WalletResponse;
import com.mee.ride.user.model.UserModel;
import com.mee.ride.user.model.UserWallet;
import com.mee.ride.user.repository.UserRepository;
import com.mee.ride.user.repository.UserWalletRepository;
import com.mee.ride.user.service.UserWalletService;

@RestController
@RequestMapping("/api/user/wallet")
public class UserWalletController {
	Logger logger = LoggerFactory.getLogger(UserWalletController.class);
	private ModelMapper modelMapper;

	UserWalletRepository repository;
	UserWalletService service;
	UserRepository userRepository;

	public UserWalletController(UserWalletRepository repository, UserWalletService service,
			UserRepository userRepository) {
		super();
		this.repository = repository;
		this.service = service;
		this.userRepository = userRepository;
	}

	// ADD AMOUNT TO USER WALLET
	@PostMapping("/add_to_wallet")
	public ResponseEntity<CommonResponse> addAmountToUserWallet(@RequestParam("user_id") String user_id,
			@RequestParam("amount") String amount, @RequestParam("transaction_id") String transaction_id) {

		UserModel userModel = userRepository.findByUserId(user_id);

		CommonResponse response = new CommonResponse();

		if (userModel == null) {

			response.setStatus("0");
			response.setMessage("Invalid User Id");
			return new ResponseEntity<CommonResponse>(response, HttpStatus.OK);
		}

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();
		String current_date = formatter.format(date);
		UserWallet wallet = new UserWallet();
		wallet.setUserId(user_id);
		wallet.setType("CREDIT");
		wallet.setTimeStamp(current_date);
		wallet.setAmount(Double.parseDouble(amount));
		wallet.setTransactionId(transaction_id);

		service.addToWallet(wallet);

		response.setStatus("1");
		response.setMessage("Wallet Amount Added");

		return new ResponseEntity<CommonResponse>(response, HttpStatus.OK);
	}
	
	// GET USER WALLET AMOUNT
		@PostMapping("/get_wallet_amount")
		public ResponseEntity<WalletResponse> getWalletAmount(@RequestParam("user_id") String user_id) {
			UserModel userModel = userRepository.findByUserId(user_id);
			
			WalletResponse response = new WalletResponse();

			if (userModel == null) {

				response.setStatus("0");
				response.setMessage("Invalid Rider Id");
				response.setTotal_amount(0.0);
				response.setCredit_amount(0.0);
				response.setDebit_amount(0.0);
				response.setBalance_amount(0.0);
				return new ResponseEntity<WalletResponse>(response, HttpStatus.OK);
			}

			List<UserWallet> c_walletList = repository.findAllByUserIdAndType(user_id, "CREDIT");
			List<UserWallet> d_walletList = repository.findAllByUserIdAndType(user_id, "DEBIT");
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
	
}
