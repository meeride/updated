package com.mee.ride.rider.model;

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
@Table(name="rider_wallet_tbl")
@NoArgsConstructor
@Getter
@Setter
public class RiderWallet {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long ID;
	
	@Column(name = "rider_id")
	private String riderId;
	
	@Column(name = "order_id")
	private String orderId;
	
	@Column(name = "transaction_id", length = 1000)
	private String transactionId;
	
	@Column(name = "amount", length = 1000)
	private Double amount;
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "timestamp")
	private String timeStamp;
	
}
