package com.mee.ride.user.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.mee.ride.rider.responses.CommonResponse;
import com.mee.ride.rider.utils.CalculateDistance;
import com.mee.ride.rider.utils.UploadImage;
import com.mee.ride.user.dto.UserLoginDto;
import com.mee.ride.user.model.BannerModel;
import com.mee.ride.user.model.UserModel;
import com.mee.ride.user.repository.BannerRepository;
import com.mee.ride.user.responses.BannerResponse;
import com.mee.ride.user.responses.UserRegisterResponse;
import com.mee.ride.user.service.BannerService;

@Controller
@RequestMapping("/api/user")
public class BannerController {

	Logger logger = LoggerFactory.getLogger(BannerController.class);

	private BannerService service;
	private BannerRepository repository;

	@Value("${project.image}")
	private String path;

	public BannerController(BannerService service, BannerRepository repository) {
		super();
		this.service = service;
		this.repository = repository;
	}

	// GET ALL BANNER
	@GetMapping("/banners")
	public ResponseEntity<BannerResponse> getBanners() {
		List<BannerModel> list = service.getAllBanners();
		BannerResponse response = new BannerResponse();
		if (list.size()==0) {
			response.setStatus("0");
			response.setMessage("No Banners Found");
			return new ResponseEntity<BannerResponse>(response, HttpStatus.OK);
		}
		
		response.setStatus("1");
		response.setMessage("Banner List");
		response.setList(list);
		
		return new ResponseEntity<BannerResponse>(response, HttpStatus.OK);
	}
	
	@GetMapping("/test_api")
	public ResponseEntity<CommonResponse> testApi() {
		//17.723118741184614, 83.31793483236699 - Siripuram
		//17.68765746436264, 83.20290933597468 - Gajuwaka
		
		double distance =  CalculateDistance.distFrom(17.723118741184614, 83.31793483236699, 17.68765746436264, 83.20290933597468);
		CommonResponse response = new CommonResponse();
		response.status = "1";
		response.message = "Distance "+distance;
		
		return new ResponseEntity<CommonResponse>(response, HttpStatus.OK);
	}

	// ADD BANNERS
	@PostMapping("/add_banner")
	public ResponseEntity<CommonResponse> addBanners(  @RequestParam("text") String text,  
			@RequestParam("image") MultipartFile image) {

		logger.info("BANNER : " + image.toString());
		//path = path+"/banners";
		String banner_image_path = UploadImage.saveImage(path, image);
		BannerModel model = new BannerModel();
		model.setText(text);
		model.setImage(banner_image_path);
		service.addBanner(model);

		CommonResponse response = new CommonResponse();
		response.status = "1";
		response.message = "Banner Added Successfully";
		return new ResponseEntity<CommonResponse>(response, HttpStatus.OK);
	}

	// DELTE SINGLE BANNER
	@PostMapping("/delete_banner")
	public ResponseEntity<CommonResponse> deleteSingleBanner(@RequestParam("id") String id) {

		/*
		 * BannerModel e_model = repository.findById(Long.parseLong(id));
		 * 
		 * logger.info("DELETE BANNER ID : " + id); CommonResponse response = new
		 * CommonResponse(); if (e_model == null) { response.status = "0";
		 * response.message = "Invalid Banner Id"; return new
		 * ResponseEntity<CommonResponse>(response, HttpStatus.OK); }
		 */
		CommonResponse response = new CommonResponse();

		service.deleteBanner(Long.parseLong(id));
		response.status = "1";
		response.message = "Banner Deleted Successfully";
		return new ResponseEntity<CommonResponse>(response, HttpStatus.OK);

	}

}
