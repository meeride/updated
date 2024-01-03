package com.mee.ride.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mee.ride.admin.model.AdminModel;

public interface AdminRepository extends JpaRepository<AdminModel, Long>{
	AdminModel findByAdminEmailAndAdminPassword(String email, String password);
	AdminModel findByAdminEmail(String email);

}
