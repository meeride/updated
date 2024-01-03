package com.mee.ride.rider.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mee.ride.rider.dto.RegisterDto;
import com.mee.ride.rider.model.RegisterRider;
import com.mee.ride.rider.model.Vehicles;
import com.mee.ride.rider.repository.VehiclesRepository;
import com.mee.ride.rider.responses.CommonResponse;
import com.mee.ride.rider.responses.RegisterResponse;
import com.mee.ride.rider.responses.VehicleUpdateResponse;
import com.mee.ride.rider.service.VehiclesService;
import com.mee.ride.rider.utils.UploadImage;

@RestController
@RequestMapping("/api/rider")
public class VehiclesController {

	private VehiclesService vService;
	private VehiclesRepository vRepository;
	Logger logger = LoggerFactory.getLogger(VehiclesController.class);

	@Value("${project.image}")
	private String path;

	@Autowired
	private ModelMapper modelMapper;

	public VehiclesController(VehiclesService vService, VehiclesRepository vRepository) {
		super();
		this.vService = vService;
		this.vRepository = vRepository;
	}

	// VEHICLES REGISTER
	@PostMapping("/register_vehicle")
	public ResponseEntity<CommonResponse> registerUser(@RequestParam("vehicle_name") String name,@RequestParam("vehicle_price") String price,
			@RequestParam("vehicle_image") MultipartFile vehicle_image) {

		int min = 10000;
		int max = 99999;
		int random_number = (int) (Math.random() * (max - min + 1) + min);
		String vehicle_id = "VEHICLE_" + random_number;

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();
		String current_date = formatter.format(date);
		//path = path+"/vehicles";
		String vehicle_image_path = UploadImage.saveImage(path, vehicle_image);

		Vehicles vehicles = new Vehicles();
		vehicles.setVehicleId(vehicle_id);
		vehicles.setRegisterDate(current_date);
		vehicles.setVehicleName(name);
		vehicles.setPrice(price);
		vehicles.setVehicleImage(vehicle_image_path);

		Vehicles data = vService.registerVehicle(vehicles);
		CommonResponse response = new CommonResponse();
		response.status = "1";
		response.message = "Vehicle Added Successfully";
		return new ResponseEntity<CommonResponse>(response, HttpStatus.OK);
	}

	// DELETE VEHICLE
	@PostMapping("/delete_vehicle")
	public ResponseEntity<CommonResponse> deleteSingleVehicle(@RequestParam("vehicle_id") String vehicle_id) {
		Vehicles vehicles = vRepository.findByVehicleId(vehicle_id);

		logger.info("DELETE VEHICLE VEHICLE_ID : " + vehicle_id);
		CommonResponse response = new CommonResponse();
		if (vehicles == null) {

			response.status = "0";
			response.message = "Invalid Vehicle Id";
			return new ResponseEntity<CommonResponse>(response, HttpStatus.OK);
		}

		vService.deleteVehicle(vehicles);
		response.status = "1";
		response.message = "Vehicle Deleted Successfully";
		return new ResponseEntity<CommonResponse>(response, HttpStatus.OK);

	}

	// GET ALL VEHICLES LIST
	@GetMapping("/vehicles_list")
	public ResponseEntity<List<Vehicles>> getAllRidersList() {
		List<Vehicles> response = vService.getAllVehicles();

		return new ResponseEntity<List<Vehicles>>(response, HttpStatus.OK);
	}
	
	// GET SINGLE VEHICLE DETAIL
		@PostMapping("/get_single_vehicle")
		public ResponseEntity<VehicleUpdateResponse> getSingleVehicle(@RequestParam("vehicle_id") String vehicle_id) {
			Vehicles vehicles = vRepository.findByVehicleId(vehicle_id);

			logger.info("GET SINGLE VEHICLE VEHICLE_ID : " + vehicle_id);
			VehicleUpdateResponse response = new VehicleUpdateResponse();
			if (vehicles == null) {

				response.setStatus("0");
				response.setMessage("Invalid Vehicle Id");
				return new ResponseEntity<VehicleUpdateResponse>(response, HttpStatus.OK);
			}

			response.setStatus("1");
			response.setMessage("Vehicle Details");
			response.setVehicle(vehicles);
			
			return new ResponseEntity<VehicleUpdateResponse>(response, HttpStatus.OK);

		}
	
	//UPDATE VEHICLE
	@PostMapping("/update_vehicle")
	public ResponseEntity<VehicleUpdateResponse> updateSingleVehicle(@RequestParam("vehicle_id") String vehicle_id,
			@RequestParam("vehicle_name") String vehicle_name,
			@RequestParam("vehicle_image") MultipartFile vehicle_image){
		Vehicles vehicles = vRepository.findByVehicleId(vehicle_id);
		logger.info("UPDATE VEHICLE VEHICLE_ID : " + vehicle_id);
		VehicleUpdateResponse response = new VehicleUpdateResponse();
		if (vehicles == null) {
			response.setStatus("0");
			response.setMessage("Invalid Vehicle Id");
			return new ResponseEntity<VehicleUpdateResponse>(response, HttpStatus.OK);
		}
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();
		String current_date = formatter.format(date);
		//path = path+"/vehicles";
		String vehicle_image_path = UploadImage.saveImage(path, vehicle_image);
		
		vehicles.setVehicleName(vehicle_name);
		vehicles.setVehicleImage(vehicle_image_path);
		
		Vehicles updatedVehicle = vService.updateVehicle(vehicles);
		
		response.setStatus("1");
		response.setMessage("Vehicle Updated Successfully");
		response.setVehicle(updatedVehicle);
		return new ResponseEntity<VehicleUpdateResponse>(response, HttpStatus.OK);
	}
	
	//UPDATE VEHICLE PRICE
	@PostMapping("/update_price")
	public ResponseEntity<CommonResponse> updateVehiclePrice(@RequestParam("vehicle_id") String vehicle_id,
			@RequestParam("vehicle_price") String vehicle_price){
		
		Vehicles vehicles = vRepository.findByVehicleId(vehicle_id);
		
		logger.info("UPDATE VEHICLE PRICE VEHICLE_ID : " + vehicle_id);
		
		CommonResponse response = new CommonResponse();
		
		if (vehicles == null) {
			response.setStatus("0");
			response.setMessage("Invalid Vehicle Id");
			return new ResponseEntity<CommonResponse>(response, HttpStatus.OK);
		}
		
		vehicles.setPrice(vehicle_price);
		vService.updateVehicle(vehicles);
		
		response.setStatus("1");
		response.setMessage("Vehicle Price Updated Successfully");
		return new ResponseEntity<CommonResponse>(response, HttpStatus.OK);
	}

}
