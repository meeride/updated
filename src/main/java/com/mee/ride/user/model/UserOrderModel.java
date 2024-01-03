package com.mee.ride.user.model;

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
@Table(name="order_tbl")
@NoArgsConstructor
@Getter
@Setter
public class UserOrderModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long ID;
	
	@Column(name = "orderId")
	private String orderId;
	
	@Column(name = "userId")
	private String userId;
	
	@Column(name = "riderId")
	private String riderId;
	
	@Column(name = "pickAddress", length = 2000)
	private String pickAddress;
	
	@Column(name = "dropAddress", length = 2000)
	private String dropAddress;
	
	@Column(name = "pickLat")
	private double pickLat ;
	
	@Column(name = "pickLon")
	private double pickLon;
	
	@Column(name = "dropLat")
	private double dropLat ;
	
	@Column(name = "dropLon")
	private double dropLon;
	
	@Column(name = "km")
	private double km;
	
	@Column(name = "totalAmount")
	private double totalAmount;
	
	@Column(name = "couponId")
	private String couponId;
	
	@Column(name = "couponAmount")
	private double couponAmount;
	
	@Column(name = "paymentType")
	private String paymentType;
	
	@Column(name = "transactionId")
	private String transactionId;
	
	@Column(name = "vehicleTypeId")
	private String vehicleTypeId;
	
	@Column(name = "orderStatus")
	private String orderStatus;
	
	@Column(name = "cancelReason", length = 2000)
	private String cancelReason;
	
	@Column(name = "rider_review_rating")
	private double rider_review_rating;
	
	@Column(name = "rider_review_rating_text", length = 2000)
	private String rider_review_rating_text;
	
	@Column(name = "isReviewed")
	private String isReviewed;
	
	@Column(name = "riderCommission")
	private double riderCommission;
	
	@Column(name = "orderDate")
	private String orderDate;
	
	@Column(name = "pickTime")
	private String pickTime;
	
	@Column(name = "dropTime")
	private String dropTime;
	
	@Column(name = "duration") 
	private String duration;
	
	@Column(name = "otp")
	private int otp;
	
	
	
	
	
}
