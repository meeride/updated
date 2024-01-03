package com.mee.ride.user.model;

import com.mee.ride.rider.model.RiderWallet;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Entity
@Table(name="user_wallet_tbl")
@NoArgsConstructor
@Getter
@Setter
public class UserWallet {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long ID;
	
	@Column(name = "user_id")
	private String userId;
	
	@Column(name = "transaction_id", length = 1000)
	private String transactionId;
	
	@Column(name = "amount", length = 1000)
	private Double amount;
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "timestamp")
	private String timeStamp;
}
