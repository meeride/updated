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
@Table(name="rider_reviews_tbl")
@NoArgsConstructor
@Getter
@Setter
public class RiderReview {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long ID;
	
	@Column(name = "rider_id")
	private String riderId;
	
	@Column(name = "user_id")
	private String userId;
	
	@Column(name = "order_id")
	private String orderId;
	
	@Column(name = "stars")
	private String stars;
	
	@Column(name = "review", length = 1000)
	private String review;
	
	@Column(name = "review_date")
	private String reviewDate;
	
}
