package com.mee.ride.user.service;

import java.util.List;

import com.mee.ride.user.model.BannerModel;

public interface BannerService {
	List<BannerModel> getAllBanners();
	void addBanner(BannerModel bannerModel);
	void deleteBanner(Long id);
}
