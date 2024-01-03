package com.mee.ride.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mee.ride.user.model.UserModel;

public interface UserRepository extends JpaRepository<UserModel, Long> {
	
	UserModel findByEmail(String email);
	UserModel findByMobile(String mobile);
	UserModel findByUserId(String user_id);
	UserModel findByEmailAndPassword(String email, String password);

}
