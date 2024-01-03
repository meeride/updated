package com.mee.ride.rider.controller;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mee.ride.rider.model.RegisterRider;
import com.mee.ride.rider.repository.RiderRegisterRepository;
import com.mee.ride.rider.responses.AllRidersResponse;
import com.mee.ride.rider.responses.CommonResponse;
import com.mee.ride.rider.responses.FirebaseTokenUpdateResponse;
import com.mee.ride.rider.responses.LoginResponse;
import com.mee.ride.rider.responses.RegisterResponse;
import com.mee.ride.rider.responses.RiderCommissionResponse;
import com.mee.ride.rider.responses.RiderStatusResponse;
import com.mee.ride.rider.responses.UpdateRiderProfileImageResponse;
import com.mee.ride.rider.service.MyFirebaseMessagingService;
import com.mee.ride.rider.service.RiderRegisterService;
import com.mee.ride.rider.utils.UploadImage;
import com.mee.ride.user.model.Note;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.mee.ride.rider.dto.*;

@RestController
@RequestMapping("/api/rider")
public class RiderRegisterController {

	public RiderRegisterService riderRegisterService;
	private RiderRegisterRepository riderRegisterRepository;
	private final MyFirebaseMessagingService firebaseService;
	Logger logger = LoggerFactory.getLogger(RiderRegisterController.class);

	@Value("${project.image}")
	private String path;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	public RiderRegisterController(RiderRegisterService riderRegisterService,
			RiderRegisterRepository riderRegisterRepository, MyFirebaseMessagingService firebaseService) {
		super();
		this.riderRegisterService = riderRegisterService;
		this.riderRegisterRepository = riderRegisterRepository;
		this.firebaseService = firebaseService;
	}

	// RIDER REGISTER API
	@PostMapping("/register")
	public ResponseEntity<RegisterResponse> registerUser(@RequestParam("name") String name,
			@RequestParam("email") String email, @RequestParam("mobile") String mobile,
			@RequestParam("password") String password, @RequestParam("address") String address,
			@RequestParam("vehicle_type_id") String vehicle_type_id,
			@RequestParam("vehicle_name") String vehicle_name,
			@RequestParam("vehicle_number") String vehicle_number,
			@RequestParam("bank_name") String bank_name,
			@RequestParam("account_number") String account_number,
			@RequestParam("ifsc") String ifsc,
			@RequestParam("name_on_account") String name_on_account,
			@RequestParam("vehicle_image") MultipartFile vehicle_image,
			@RequestParam("rider_image") MultipartFile rider_image,
			@RequestParam("license_image") MultipartFile license_image,
			@RequestParam("c_book_image") MultipartFile c_book_image,
			@RequestParam("aadhar_image") MultipartFile aadhar_image) {

		RegisterRider e_rider = riderRegisterRepository.findByEmail(email);
		RegisterRider m_rider = riderRegisterRepository.findByMobile(mobile);
		if (e_rider != null) {
			RegisterResponse response = new RegisterResponse();
			response.status = "0";
			response.message = "Email Already Registered";
			return new ResponseEntity<RegisterResponse>(response, HttpStatus.OK);
		}

		if (m_rider != null) {
			RegisterResponse response = new RegisterResponse();
			response.status = "0";
			response.message = "Phone Already Registered";
			return new ResponseEntity<RegisterResponse>(response, HttpStatus.OK);
		}
		int min = 10000;
		int max = 99999;
		int random_number = (int) (Math.random() * (max - min + 1) + min);
		String rider_id = "RIDER_" + random_number;

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();
		String current_date = formatter.format(date);
		//path = path+"/riders/"+mobile;
		String vehicle_image_path = UploadImage.saveImage(path, vehicle_image);
		String rider_image_path = UploadImage.saveImage(path, rider_image);
		String license_image_path = UploadImage.saveImage(path, license_image);
		String c_book_image_path = UploadImage.saveImage(path, c_book_image);
		String aadhar_image_path = UploadImage.saveImage(path, aadhar_image);

		RegisterRider rider = new RegisterRider();

		rider.setRiderId(rider_id);
		rider.setApprove_status("0");
		rider.setRiderStatus("0");
		rider.setRegister_date(current_date);
		rider.setName(name);
		rider.setEmail(email);
		rider.setMobile(mobile);
		rider.setPassword(password);
		rider.setAddress(address);
		rider.setVehicle_type_id(vehicle_type_id);
		rider.setVehicle_name(vehicle_name);
		rider.setVehicle_number(vehicle_number);
		rider.setBank_name(bank_name);
		rider.setAccount_number(account_number);
		rider.setIfsc(ifsc);
		rider.setName_on_account(name_on_account);
		rider.setVehicle_image(vehicle_image_path);
		rider.setRider_image(rider_image_path);
		rider.setLicense_image(license_image_path);
		rider.setC_book_image(c_book_image_path);
		rider.setAadhar_image(aadhar_image_path);

		RegisterRider data = riderRegisterService.registerRider(rider);
		RegisterResponse response = new RegisterResponse();
		response.status = "1";
		response.message = "Registered Successfully";
		response.rider_id = data.getRiderId();
		return new ResponseEntity<RegisterResponse>(response, HttpStatus.OK);
	}
	
	// RIDER UPDATE API
	@PostMapping("/update")
	public ResponseEntity<RegisterResponse> updateRider(@RequestParam("rider_id") String rider_id,
			@RequestParam("name") String name,
			@RequestParam("email") String email, @RequestParam("mobile") String mobile,
			@RequestParam("password") String password, @RequestParam("address") String address,
			@RequestParam("vehicle_type_id") String vehicle_type_id,
			@RequestParam("vehicle_name") String vehicle_name,
			@RequestParam("vehicle_number") String vehicle_number,
			@RequestParam("bank_name") String bank_name,
			@RequestParam("account_number") String account_number,
			@RequestParam("ifsc") String ifsc,
			@RequestParam("name_on_account") String name_on_account,
			@RequestParam("vehicle_image") MultipartFile vehicle_image,
			@RequestParam("rider_image") MultipartFile rider_image,
			@RequestParam("license_image") MultipartFile license_image,
			@RequestParam("c_book_image") MultipartFile c_book_image,
			@RequestParam("aadhar_image") MultipartFile aadhar_image) {

		RegisterRider rider = riderRegisterRepository.findByRiderId(rider_id);
		if (rider == null) {
			RegisterResponse response = new RegisterResponse();
			response.status = "0";
			response.message = "Invalid Rider ID";
			return new ResponseEntity<RegisterResponse>(response, HttpStatus.OK);
		}
	
		String vehicle_image_path = UploadImage.saveImage(path, vehicle_image);
		String rider_image_path = UploadImage.saveImage(path, rider_image);
		String license_image_path = UploadImage.saveImage(path, license_image);
		String c_book_image_path = UploadImage.saveImage(path, c_book_image);
		String aadhar_image_path = UploadImage.saveImage(path, aadhar_image);

		
		
		rider.setName(name);
		rider.setEmail(email);
		rider.setMobile(mobile);
		rider.setPassword(password);
		rider.setAddress(address);
		rider.setVehicle_type_id(vehicle_type_id);
		rider.setVehicle_name(vehicle_name);
		rider.setVehicle_number(vehicle_number);
		rider.setBank_name(bank_name);
		rider.setAccount_number(account_number);
		rider.setIfsc(ifsc);
		rider.setName_on_account(name_on_account);
		rider.setVehicle_image(vehicle_image_path);
		rider.setRider_image(rider_image_path);
		rider.setLicense_image(license_image_path);
		rider.setC_book_image(c_book_image_path);
		rider.setAadhar_image(aadhar_image_path);

		RegisterRider data = riderRegisterService.updateRider(rider);
		RegisterResponse response = new RegisterResponse();
		response.status = "1";
		response.message = "Updated Successfully";
		response.rider_id = data.getRiderId();
		return new ResponseEntity<RegisterResponse>(response, HttpStatus.OK);
	}
	

	// RIDER LOGIN API
	@PostMapping("/login")
	public ResponseEntity<LoginResponse> loginUser(@RequestParam("mobile") String mobile,
			@RequestParam("password") String password) {
		RegisterRider e_rider = riderRegisterRepository.findByMobileAndPassword(mobile, password);

		logger.info("LOGIN MOBILE : " + mobile);
		logger.info("LOGIN PASSWORD : " + password);

		if (e_rider == null) {
			LoginResponse response = new LoginResponse();
			response.status = "0";
			response.message = "Invalid Mobile or password";
			return new ResponseEntity<LoginResponse>(response, HttpStatus.OK);
		}

		if (e_rider.getApprove_status().equals("0")) {
			LoginResponse response = new LoginResponse();
			response.status = "2";
			response.message = "Your Account has not been Approved Yet";
			return new ResponseEntity<LoginResponse>(response, HttpStatus.OK);
		}
		
		if (e_rider.getApprove_status().equals("2")) {
			LoginResponse response = new LoginResponse();
			response.status = "3";
			response.message = e_rider.getReject_reason();
			return new ResponseEntity<LoginResponse>(response, HttpStatus.OK);
		}

		LoginResponse response = new LoginResponse();
		response.status = "1";
		response.message = "Login Successfully";

		RegisterDto dto = this.modelMapper.map(e_rider, RegisterDto.class);
		response.rider_data = dto;
		return new ResponseEntity<LoginResponse>(response, HttpStatus.OK);

	}

	// CHECK IF MOBILE NUMBER EXISTS
	@PostMapping("/check_mobile")
	public ResponseEntity<CommonResponse> checkMobile(@RequestParam("mobile") String mobile) {
		logger.info("CHECK MOBILE : " + mobile);

		RegisterRider e_rider = riderRegisterRepository.findByMobile(mobile);
		CommonResponse response = new CommonResponse();
		if (e_rider == null) {
			response.setStatus("1");
			response.setMessage("Mobile Number doesn't exist");
			return new ResponseEntity<CommonResponse>(response, HttpStatus.OK);
		}
		response.setStatus("0");
		response.setMessage("Mobile Number already exist");

		return new ResponseEntity<CommonResponse>(response, HttpStatus.OK);
	}

	// UPDATE FIREBASE TOKEN
	@PostMapping("/update_rider_firebase_token")
	public ResponseEntity<FirebaseTokenUpdateResponse> updateFirebaseToken(@RequestParam("rider_id") String rider_id,
			@RequestParam("firebase_token") String firebase_token) {
		RegisterRider e_rider = riderRegisterRepository.findByRiderId(rider_id);

		logger.info("UPDATE FIREBASE TOKEN RIDER_ID : " + rider_id);
		logger.info("UPDATE FIREBASE TOKEN firebase_token : " + firebase_token);

		if (e_rider == null) {
			FirebaseTokenUpdateResponse response = new FirebaseTokenUpdateResponse();
			response.status = "0";
			response.message = "Invalid Rider Id";
			return new ResponseEntity<FirebaseTokenUpdateResponse>(response, HttpStatus.OK);
		}

		e_rider.setFirebase_token(firebase_token);
		riderRegisterService.updateRiderToken(e_rider);

		FirebaseTokenUpdateResponse response = new FirebaseTokenUpdateResponse();
		response.status = "1";
		response.message = "Updated Successfully";
		return new ResponseEntity<FirebaseTokenUpdateResponse>(response, HttpStatus.OK);
	}

	// ACTIVE OR DEACTIVE RIDER
	@PostMapping("/approve_rider")
	public ResponseEntity<CommonResponse> approveRider(@RequestParam("rider_id") String rider_id,
			@RequestParam("status") String status) {
		RegisterRider e_rider = riderRegisterRepository.findByRiderId(rider_id);

		logger.info("APPROVE RIDER RIDER_ID : " + rider_id);
		logger.info("APPROVE RIDER STATUS : " + status);

		if (e_rider == null) {
			CommonResponse response = new CommonResponse();
			response.status = "0";
			response.message = "Invalid Rider Id";
			return new ResponseEntity<CommonResponse>(response, HttpStatus.OK);
		}

		e_rider.setApprove_status(status);
		riderRegisterService.approveRider(e_rider);

		CommonResponse response = new CommonResponse();
		response.status = "1";
		response.message = "Status Updated Successfully";
		return new ResponseEntity<CommonResponse>(response, HttpStatus.OK);
	}

	// REJECT RIDER
	@PostMapping("/reject_rider")
	public ResponseEntity<CommonResponse> rejectRider(@RequestParam("rider_id") String rider_id,
			@RequestParam("reason") String reason) {
		RegisterRider e_rider = riderRegisterRepository.findByRiderId(rider_id);

		logger.info("APPROVE RIDER RIDER_ID : " + rider_id);
		logger.info("APPROVE RIDER REASON : " + reason);

		if (e_rider == null) {
			CommonResponse response = new CommonResponse();
			response.status = "0";
			response.message = "Invalid Rider Id";
			return new ResponseEntity<CommonResponse>(response, HttpStatus.OK);
		}

		e_rider.setApprove_status("2");
		e_rider.setReject_reason(reason);
		riderRegisterService.approveRider(e_rider);

		CommonResponse response = new CommonResponse();
		response.status = "1";
		response.message = "Rider Rejected Successfully";
		return new ResponseEntity<CommonResponse>(response, HttpStatus.OK);
	}
	
	
	// UPDATE RIDER COMISSION
	@PostMapping("/update_rider_comission")
	public ResponseEntity<CommonResponse> updateRiderComission(@RequestParam("rider_id") String rider_id,
			@RequestParam("comission") String comission) {
		
		logger.info("UPDATE FIREBASE TOKEN RIDER_ID : " + rider_id);
		logger.info("UPDATE FIREBASE TOKEN comission : " + comission);
		
		RegisterRider e_rider = riderRegisterRepository.findByRiderId(rider_id);
		CommonResponse response = new CommonResponse();
		if (e_rider == null) {
			
			response.setStatus("0");
			response.setMessage("Invalid Rider Id");
			return new ResponseEntity<CommonResponse>(response, HttpStatus.OK);
		}

		e_rider.setComission(comission);
		riderRegisterService.updateRiderToken(e_rider);

		
		response.setStatus("1");
		response.setMessage("Comission Updated Successfully");
		return new ResponseEntity<CommonResponse>(response, HttpStatus.OK);
	}
	
	// GET RIDER COMISSION
	@PostMapping("/get_rider_comission")
	public ResponseEntity<RiderCommissionResponse> getRiderComission(@RequestParam("rider_id") String rider_id) {
		
		logger.info("GET RIDER COMMISSION RIDER_ID : " + rider_id);
		
		
		RegisterRider e_rider = riderRegisterRepository.findByRiderId(rider_id);
		RiderCommissionResponse response = new RiderCommissionResponse();
		if (e_rider == null) {
			
			response.setStatus("0");
			response.setMessage("Invalid Rider Id");
			return new ResponseEntity<RiderCommissionResponse>(response, HttpStatus.OK);
		}

		

		
		response.setStatus("1");
		response.setMessage("Rider Comission");
		response.setCommission(e_rider.getComission());
		return new ResponseEntity<RiderCommissionResponse>(response, HttpStatus.OK);
	}
	

	// RIDER STATUS UPDATE
	@PostMapping("/update_rider_status")
	public ResponseEntity<CommonResponse> updateRiderStatus(@RequestParam("rider_id") String rider_id,
			@RequestParam("status") String status) {
		RegisterRider e_rider = riderRegisterRepository.findByRiderId(rider_id);

		logger.info("UPDATE RIDER RIDER_ID : " + rider_id);
		logger.info("UPDATE RIDER STATUS : " + status);

		if (e_rider == null) {
			CommonResponse response = new CommonResponse();
			response.status = "0";
			response.message = "Invalid Rider Id";
			return new ResponseEntity<CommonResponse>(response, HttpStatus.OK);
		}

		e_rider.setRiderStatus(status);
		riderRegisterService.updateRider(e_rider);

		CommonResponse response = new CommonResponse();
		response.status = "1";
		response.message = "Status Updated Successfully";
		return new ResponseEntity<CommonResponse>(response, HttpStatus.OK);
	}

	// GET RIDER STATUS
	@PostMapping("/get_rider_status")
	public ResponseEntity<RiderStatusResponse> getRiderStatus(@RequestParam("rider_id") String rider_id) {
		RegisterRider e_rider = riderRegisterRepository.findByRiderId(rider_id);

		logger.info("GET RIDER STATUS RIDER_ID : " + rider_id);

		if (e_rider == null) {
			RiderStatusResponse response = new RiderStatusResponse();
			response.status = "0";
			response.message = "Invalid Rider Id";
			response.rider_status = "No Response";
			return new ResponseEntity<RiderStatusResponse>(response, HttpStatus.OK);
		}

		RiderStatusResponse response = new RiderStatusResponse();
		response.status = "1";
		response.message = "Rider Status";
		response.rider_status = e_rider.getRiderStatus();
		return new ResponseEntity<RiderStatusResponse>(response, HttpStatus.OK);
	}

	// GET SINGLE RIDER DETAILS
	@PostMapping("/get_rider_detail")
	public ResponseEntity<LoginResponse> getRiderDetails(@RequestParam("rider_id") String rider_id) {
		RegisterRider e_rider = riderRegisterRepository.findByRiderId(rider_id);

		logger.info("GET RIDER DETAILS RIDER_ID : " + rider_id);

		LoginResponse response = new LoginResponse();

		if (e_rider == null) {
			response.status = "0";
			response.message = "Invalid Rider Id";
			return new ResponseEntity<LoginResponse>(response, HttpStatus.OK);
		}

		response.status = "1";
		response.message = "Rider Details";
		RegisterDto dto = this.modelMapper.map(e_rider, RegisterDto.class);
		response.rider_data = dto;
		return new ResponseEntity<LoginResponse>(response, HttpStatus.OK);
	}

	// DELTE RIDER
	@PostMapping("/delete_rider")
	public ResponseEntity<CommonResponse> deleteSingleRider(@RequestParam("rider_id") String rider_id) {
		RegisterRider e_rider = riderRegisterRepository.findByRiderId(rider_id);

		logger.info("DELETE RIDER RIDER_ID : " + rider_id);
		CommonResponse response = new CommonResponse();
		if (e_rider == null) {

			response.status = "0";
			response.message = "Invalid Rider Id";
			return new ResponseEntity<CommonResponse>(response, HttpStatus.OK);
		}

		riderRegisterService.deleteRider(e_rider);

		response.status = "1";
		response.message = "Rider Deleted Successfully";
		return new ResponseEntity<CommonResponse>(response, HttpStatus.OK);

	}

	// GET ALL RIDERS LIST
	@GetMapping("/riders_list")
	public ResponseEntity<AllRidersResponse> getAllRidersList() {
		AllRidersResponse response = new AllRidersResponse();

		List<RegisterDto> list = riderRegisterService.getAllRiders();
		response.setStatus("1");
		response.setMessage("Riders List");
		response.setList(list);

		return new ResponseEntity<AllRidersResponse>(response, HttpStatus.OK);
	}

	// UPDATE RIDER PROFILE IMAGE
	@PostMapping("/update_rider_profile_image")
	public ResponseEntity<UpdateRiderProfileImageResponse> updateRiderProfileImage(
			@RequestParam("rider_id") String rider_id, @RequestParam("rider_image") MultipartFile rider_image) {

		RegisterRider e_rider = riderRegisterRepository.findByRiderId(rider_id);
		logger.info("UPDATE RIDER PROFILE IMAGE RIDER_ID : " + rider_id);

		UpdateRiderProfileImageResponse response = new UpdateRiderProfileImageResponse();

		if (e_rider == null) {

			response.setStatus("0");
			response.setMessage("Invalid Rider Id");
			return new ResponseEntity<UpdateRiderProfileImageResponse>(response, HttpStatus.OK);
		}

		String rider_image_path = UploadImage.saveImage(path, rider_image);
		e_rider.setRider_image(rider_image_path);

		RegisterRider r_rider = riderRegisterService.updateRider(e_rider);
		response.setStatus("1");
		response.setMessage("Rider Profile Image Updated Successfully");
		response.setRider_image(r_rider.getRider_image());
		return new ResponseEntity<UpdateRiderProfileImageResponse>(response, HttpStatus.OK);

	}
	
	//UPDATE RIDER PROFILE
	@PostMapping("/update_rider_profile")
	public ResponseEntity<CommonResponse> updateRiderProfile(@RequestParam("rider_id") String rider_id,
			@RequestParam("name") String name, @RequestParam("email") String email,@RequestParam("address") String address) {
		
		logger.info("UPDATE RIDER PROFILE RIDER_ID : " + rider_id);
		RegisterRider e_rider = riderRegisterRepository.findByRiderId(rider_id);
		CommonResponse response = new CommonResponse();
		if (e_rider == null) {
			response.setStatus("0");
			response.setMessage("Invalid Rider Id");
			return new ResponseEntity<CommonResponse>(response, HttpStatus.OK);
		}
		e_rider.setName(name);
		e_rider.setEmail(email);
		e_rider.setAddress(address);
		riderRegisterService.updateRider(e_rider);	
		response.setStatus("1");
		response.setMessage("Profile Updated Successfully");
		return new ResponseEntity<CommonResponse>(response, HttpStatus.OK);
	}
	
	//UPDATE BANK DETAILS
	@PostMapping("/update_rider_bank")
	public ResponseEntity<CommonResponse> updateRiderBank(@RequestParam("rider_id") String rider_id,
			@RequestParam("bank_name") String bank_name, @RequestParam("account_number") String account_number,
			@RequestParam("ifsc") String ifsc, @RequestParam("name_on_account") String name_on_account) {
		
		logger.info("UPDATE RIDER BANK RIDER_ID : " + rider_id);
		RegisterRider e_rider = riderRegisterRepository.findByRiderId(rider_id);
		CommonResponse response = new CommonResponse();
		if (e_rider == null) {
			response.setStatus("0");
			response.setMessage("Invalid Rider Id");
			return new ResponseEntity<CommonResponse>(response, HttpStatus.OK);
		}
		e_rider.setBank_name(bank_name);
		e_rider.setAccount_number(account_number);
		e_rider.setIfsc(ifsc);
		e_rider.setName_on_account(name_on_account);
		riderRegisterService.updateRider(e_rider);
		response.setStatus("1");
		response.setMessage("Bank Updated Successfully");
		return new ResponseEntity<CommonResponse>(response, HttpStatus.OK);
	}
	
	
	@GetMapping("/notify")
	public ResponseEntity<CommonResponse> sendDemoNotification() throws FirebaseMessagingException{
		
		String token = "f5K_jRbJSDaLAaZXyoXMLp:APA91bGxVPGv_vyU9Hjn0cKIN-PcXvBAjRuOmT6xJzfXLA1XGXCSobrZ2W-JaQPwxm5lf1FCWPaofWNOVTANJAZmm9EWRz5vVW28fAVOT4q4qqWdwHvMhzgs6AOkoiCQqRxY3GWC15bR";
		Note note = new Note();
		note.setTitle("New Ride");
		note.setBody("New Order has arrived");
		Map<String, String> map = new HashMap<>();
		map.put("order_id", "1");
		note.setData(map);
		
		firebaseService.sendNotification(note, token);
		CommonResponse response = new CommonResponse();
		response.status = "1";
		response.message = "Notification Sent";
		return new ResponseEntity<CommonResponse>(response, HttpStatus.OK);
	}

}
