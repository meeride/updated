package com.mee.ride.admin.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mee.ride.admin.model.AdminModel;
import com.mee.ride.admin.model.UtilModel;
import com.mee.ride.admin.repository.UtilsRepository;
import com.mee.ride.admin.responses.AdminResponse;
import com.mee.ride.admin.responses.UtilsResponse;
import com.mee.ride.admin.service.UtilsService;
import com.mee.ride.rider.model.Vehicles;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/utils")
public class UtilsController {
	
	UtilsRepository utilsRepository;
	UtilsService utilsService;
	@Value("${project.image}")
	private String path;
	
	
	
	
	public UtilsController(UtilsRepository utilsRepository, UtilsService utilsService) {
		super();
		this.utilsRepository = utilsRepository;
		this.utilsService = utilsService;
	}


	Logger logger = LoggerFactory.getLogger(UtilsController.class);
	
		// Update Cancellation Charges
		@PostMapping("/update_c_charges")
		public ResponseEntity<UtilsResponse> updateCancellationCharges(@RequestParam("amount") String amount) {
			UtilModel utilModel = utilsRepository.findByCid("1");
			
			UtilModel model = new UtilModel();
			if (utilModel == null) {
				model.setCid("1");
				model.setCancellation(amount);
			}else {
				model.setCid(utilModel.getCid());
				model.setCancellation(amount);
				model.setID(utilModel.getID());
			}
			
			
			
			
			utilsService.updateCancellation(model);
		
			UtilsResponse response = new UtilsResponse();
			response.setStatus("1");
			response.setMessage("Updated Successfully");
			response.setCancellation(amount);
			return new ResponseEntity<UtilsResponse>(response, HttpStatus.OK);

		}
		
		
		// GET ALL VEHICLES LIST
		@GetMapping("/get_c_charges")
		public ResponseEntity<UtilsResponse> getAllRidersList() {
			UtilModel model = utilsRepository.findByCid("1");
			//UtilModel model = utilsRepository.findByID(1);
			UtilsResponse response = new UtilsResponse();
			response.setStatus("1");
			response.setMessage("Cancellation Charges");
			response.setCancellation(model.getCancellation());
			return new ResponseEntity<UtilsResponse>(response, HttpStatus.OK);
		}
		
		//GET IMAGES
		@GetMapping("/images/{imagename}")
		public void getImages(@PathVariable("imagename") String imagename, HttpServletResponse response) {
			
			String filePath = path+File.separator+imagename;
			File file = new File(filePath);
			try {
				if (file.exists()) {
					String mimeType = URLConnection.guessContentTypeFromName(file.getName());
					if (mimeType == null) {

						mimeType = "application/octet-stream";
					}

					response.setContentType(mimeType);

					response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));

					response.setContentLength((int) file.length());

					InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

					FileCopyUtils.copy(inputStream, response.getOutputStream());
				}
				
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		}
		
}
