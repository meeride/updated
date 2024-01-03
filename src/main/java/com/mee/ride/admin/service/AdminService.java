package com.mee.ride.admin.service;


import java.util.List;

import com.mee.ride.admin.model.AdminModel;

public interface AdminService {
	AdminModel registerAdmin(AdminModel admin);
	AdminModel updateAdmin(AdminModel admin);
	List<AdminModel> getAllAdmins();
}
