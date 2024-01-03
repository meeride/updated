package com.mee.ride.admin.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mee.ride.admin.model.AdminModel;
import com.mee.ride.admin.repository.AdminRepository;
import com.mee.ride.admin.responses.AdminResponse;
import com.mee.ride.admin.responses.AllAdminsResponse;
import com.mee.ride.admin.service.AdminService;
import com.mee.ride.rider.controller.RiderRegisterController;
import com.mee.ride.rider.dto.RegisterDto;
import com.mee.ride.rider.model.RegisterRider;
import com.mee.ride.rider.model.Vehicles;
import com.mee.ride.rider.responses.CommonResponse;
import com.mee.ride.rider.responses.LoginResponse;
import com.mee.ride.rider.utils.UploadImage;
import com.mee.ride.user.dto.UserLoginDto;
import com.mee.ride.user.responses.UserListResponse;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

	AdminRepository aRepository;
	AdminService aService;

	Logger logger = LoggerFactory.getLogger(AdminController.class);

	public AdminController(AdminRepository aRepository, AdminService aService) {
		super();
		this.aRepository = aRepository;
		this.aService = aService;
	}

	// ADMINS REGISTER
	@PostMapping("/register_admin")
	public ResponseEntity<AdminResponse> registerAdmin(@RequestParam("admin_name") String admin_name,
			@RequestParam("admin_email") String admin_email, @RequestParam("admin_password") String admin_password) {
		
		AdminModel a_model = aRepository.findByAdminEmail(admin_email);
		AdminResponse response = new AdminResponse();
		if (a_model != null) {
			response.setStatus("0");
			response.setMessage("Email Already Exists");
			return new ResponseEntity<AdminResponse>(response, HttpStatus.OK);
		}

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();
		String current_date = formatter.format(date);

		AdminModel model = new AdminModel();
		model.setAdminEmail(admin_email);
		model.setAdminName(admin_name);
		model.setAdminPassword(admin_password);
		model.setRegisterDate(current_date);
		AdminModel data = aService.registerAdmin(model);
		response.setStatus("1");
		response.setMessage("Admin Added Successfully");
		response.setName(admin_name);
		return new ResponseEntity<AdminResponse>(response, HttpStatus.OK);
	}
	
	//CHANGE PASSWORD
	@PostMapping("/change_password")
	public ResponseEntity<AdminResponse> changePassword(@RequestParam("admin_email") String admin_email, 
			@RequestParam("admin_password") String admin_password) {
		
		AdminModel model = aRepository.findByAdminEmail(admin_email);
		AdminResponse response = new AdminResponse();
		if (model == null) {
			response.setStatus("0");
			response.setMessage("Invalid Email Id");
			return new ResponseEntity<AdminResponse>(response, HttpStatus.OK);
		}

		model.setAdminPassword(admin_password);
	
		AdminModel data = aService.updateAdmin(model);
		response.setStatus("1");
		response.setMessage("Password Changed Successfully");
		response.setName(model.getAdminName());
		return new ResponseEntity<AdminResponse>(response, HttpStatus.OK);
	}
	

	// ADMIN LOGIN
	@PostMapping("/login_admin")
	public ResponseEntity<AdminResponse> loginAdmin(@RequestParam("admin_email") String email,
			@RequestParam("admin_password") String password) {
		AdminModel model = aRepository.findByAdminEmailAndAdminPassword(email, password);

		logger.info("ADMIN LOGIN EMAIL : " + email);
		logger.info("ADMIN LOGIN PASSWORD : " + password);

		AdminResponse response = new AdminResponse();
		if (model == null) {
			response.setStatus("0");
			response.setMessage("Invalid Username and Password");
			return new ResponseEntity<AdminResponse>(response, HttpStatus.OK);
		}
		response.setStatus("1");
		response.setMessage("Login Successfully");
		response.setName(model.getAdminName());
		return new ResponseEntity<AdminResponse>(response, HttpStatus.OK);

	}
	
	// GET ALL ADMINS DATA
		@GetMapping("/admins_list")
		public ResponseEntity<AllAdminsResponse> getAllUsersList() {
			List<AdminModel> list = aService.getAllAdmins();
			AllAdminsResponse response = new AllAdminsResponse();
			response.setStatus("1");
			response.setMessage("Admins List");
			response.setList(list);
			return new ResponseEntity<AllAdminsResponse>(response, HttpStatus.OK);
		}

}
