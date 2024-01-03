package com.mee.ride.admin.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mee.ride.admin.model.AdminModel;
import com.mee.ride.admin.repository.AdminRepository;
import com.mee.ride.admin.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {

	AdminRepository aRepository;
	
	
	
	public AdminServiceImpl(AdminRepository aRepository) {
		super();
		this.aRepository = aRepository;
	}



	@Override
	public AdminModel registerAdmin(AdminModel admin) {
		return aRepository.save(admin);
	}



	@Override
	public AdminModel updateAdmin(AdminModel admin) {
		// TODO Auto-generated method stub
		return aRepository.save(admin);
	}



	@Override
	public List<AdminModel> getAllAdmins() {
		// TODO Auto-generated method stub
		List<AdminModel> list = aRepository.findAll();
		return list;
	}

}
