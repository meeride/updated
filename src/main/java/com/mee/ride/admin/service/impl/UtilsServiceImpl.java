package com.mee.ride.admin.service.impl;

import org.springframework.stereotype.Service;

import com.mee.ride.admin.model.UtilModel;
import com.mee.ride.admin.repository.UtilsRepository;
import com.mee.ride.admin.service.UtilsService;

@Service
public class UtilsServiceImpl implements UtilsService {
	
	//CONSTRUCTOR BASE DEPENDENCY INJECTION
	UtilsRepository repository;

	public UtilsServiceImpl(UtilsRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	public UtilModel updateCancellation(UtilModel utilModel) {
		// TODO Auto-generated method stub
		return repository.save(utilModel);
	}


	
	

}
