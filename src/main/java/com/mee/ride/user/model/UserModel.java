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
@Table(name="user_tbl")
@NoArgsConstructor
@Getter
@Setter
public class UserModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long ID;
	
	@Column(name = "userId")
	private String userId;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "gender")
	private String gender;
		
	@Column(name = "email")
	private String email;
	
	@Column(name = "mobile")
	private String mobile;
	
	@Column(name = "password")
	private String password;
	
	
	@Column(name = "referCode")
	private String referCode;
	
	@Column(name = "parentCode")
	private String parentCode;
	
	@Column(name = "userImage", length = 1000)
	private String userImage;
	
	@Column(name = "firebaseToken", length = 1000)
	private String firebaseToken;
	
	@Column(name = "approveStatus")
	private String approveStatus;
	
	@Column(name = "registerDate")
	private String registerDate;
	
	@Column(name = "wallet")
	private double wallet;
	
}
