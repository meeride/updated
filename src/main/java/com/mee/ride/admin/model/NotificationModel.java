package com.mee.ride.admin.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="notification_tbl")
public class NotificationModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long ID;
	
	@Column(name = "user_id")
	private String userId;
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "image")
	private String image;
	
	@Column(name = "title", length = 2000)
	private String title;
	
	@Column(name = "body", length = 2000)
	private String body;
	
	@Column(name = "date")
	private String date;

}
