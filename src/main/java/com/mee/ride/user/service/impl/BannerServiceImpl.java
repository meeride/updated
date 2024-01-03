package com.mee.ride.user.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mee.ride.user.model.BannerModel;
import com.mee.ride.user.repository.BannerRepository;
import com.mee.ride.user.service.BannerService;

@Service
public class BannerServiceImpl implements BannerService {
	
	private BannerRepository repository;
	
	

	public BannerServiceImpl(BannerRepository repository) {
		super();
		this.repository = repository;
	}



	@Override
	public List<BannerModel> getAllBanners() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}



	@Override
	public void addBanner(BannerModel bannerModel) {
		repository.save(bannerModel);
		
	}



	@Override
	public void deleteBanner(Long id) {
		repository.deleteById(id);
		
	}

}
