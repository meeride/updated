package com.mee.ride.user.controller;

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
import com.mee.ride.rider.model.Vehicles;
import com.mee.ride.rider.responses.CommonResponse;
import com.mee.ride.rider.utils.UploadImage;
import com.mee.ride.user.dto.UserLoginDto;
import com.mee.ride.user.model.UserModel;
import com.mee.ride.user.repository.UserRepository;
import com.mee.ride.user.responses.UserListResponse;
import com.mee.ride.user.responses.UserLoginResponse;
import com.mee.ride.user.responses.UserRegisterResponse;
import com.mee.ride.user.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
	Logger logger = LoggerFactory.getLogger(UserController.class);

	@Value("${project.image}")
	private String path;

	@Autowired
	private ModelMapper modelMapper;

	private UserService uService;
	private UserRepository vRepository;

	public UserController(UserService uService, UserRepository vRepository) {
		super();
		this.uService = uService;
		this.vRepository = vRepository;
	}

	// USER REGISTER API
	@PostMapping("/register")
	public ResponseEntity<UserRegisterResponse> registerUser(@RequestParam("name") String name,
			@RequestParam("email") String email, @RequestParam("mobile") String mobile,@RequestParam("gender") String gender,
			@RequestParam("password") String password, @RequestParam("referCode") String referCode,
			@RequestParam("parentCode") String parentCode, @RequestParam("userImage") MultipartFile userImage) {

		UserModel e_model = vRepository.findByEmail(email);
		UserModel m_model = vRepository.findByMobile(mobile);

		logger.info("REGISTER EMAIL : " + email);
		logger.info("REGISTER PASSWORD : " + password);

		UserRegisterResponse response = new UserRegisterResponse();

		if (e_model != null) {
			response.setStatus("0");
			response.setMessage("Email Already Registered");
			return new ResponseEntity<UserRegisterResponse>(response, HttpStatus.OK);
		}

		if (m_model != null) {
			response.setStatus("0");
			response.setMessage("Phone Already Registered");
			return new ResponseEntity<UserRegisterResponse>(response, HttpStatus.OK);
		}
		int min = 10000;
		int max = 99999;
		int random_number = (int) (Math.random() * (max - min + 1) + min);
		String user_id = "USER_" + random_number;

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();
		String current_date = formatter.format(date);
		//path = path+"/users/"+mobile;
		String user_image_path = UploadImage.saveImage(path, userImage);

		UserModel uModel = new UserModel();

		uModel.setUserId(user_id);
		uModel.setApproveStatus("1");
		uModel.setRegisterDate(current_date);
		uModel.setName(name);
		uModel.setGender(gender);
		uModel.setEmail(email);
		uModel.setMobile(mobile);
		uModel.setPassword(password);
		uModel.setUserImage(user_image_path);
		uModel.setReferCode(referCode);
		uModel.setParentCode(parentCode);
		uModel.setWallet(0.0);

		UserModel data = uService.registerUser(uModel);
		response.setStatus("1");
		response.setMessage("Registered Successfully");
		response.setUserId(data.getUserId());
		return new ResponseEntity<UserRegisterResponse>(response, HttpStatus.OK);
	}

	// USER LOGIN WITH EMAIL AND PASSWORD API
	@PostMapping("/login")
	public ResponseEntity<UserLoginResponse> loginUser(@RequestParam("email") String email,
			@RequestParam("password") String password) {
		UserModel uModel = vRepository.findByEmailAndPassword(email, password);

		logger.info("LOGIN EMAIL : " + email);
		logger.info("LOGIN PASSWORD : " + password);

		UserLoginResponse response = new UserLoginResponse();

		if (uModel == null) {
			response.setStatus("0");
			response.setMessage("Invalid Username or password");
			return new ResponseEntity<UserLoginResponse>(response, HttpStatus.OK);
		}

		if (uModel.getApproveStatus().equals("0")) {
			response.setStatus("0");
			response.setMessage("Your Account has not been Approved Yet");
			return new ResponseEntity<UserLoginResponse>(response, HttpStatus.OK);
		}

		response.setStatus("1");
		response.setMessage("Login Successfully");
		UserLoginDto dto = this.modelMapper.map(uModel, UserLoginDto.class);
		response.setUser_data(dto);
		return new ResponseEntity<UserLoginResponse>(response, HttpStatus.OK);

	}

	// USER LOGIN WITH PHONE API
	@PostMapping("/mobile_login")
	public ResponseEntity<UserLoginResponse> loginUserWithMobile(@RequestParam("mobile") String mobile) {
		UserModel uModel = vRepository.findByMobile(mobile);

		logger.info("LOGIN MOBILE : " + mobile);

		UserLoginResponse response = new UserLoginResponse();

		if (uModel == null) {
			response.setStatus("0");
			response.setMessage("Mobile Number doesn't exists");
			return new ResponseEntity<UserLoginResponse>(response, HttpStatus.OK);
		}

		if (uModel.getApproveStatus().equals("0")) {
			response.setStatus("0");
			response.setMessage("Your Account has not been Approved Yet");
			return new ResponseEntity<UserLoginResponse>(response, HttpStatus.OK);
		}

		response.setStatus("1");
		response.setMessage("Login Successfully");
		UserLoginDto dto = this.modelMapper.map(uModel, UserLoginDto.class);
		response.setUser_data(dto);
		return new ResponseEntity<UserLoginResponse>(response, HttpStatus.OK);

	}

	// UPDATE USER API
	@PostMapping("/update_user")
	public ResponseEntity<UserLoginResponse> updateUser(@RequestParam("userId") String userId,
			@RequestParam("name") String name, @RequestParam("email") String email,
			@RequestParam("mobile") String mobile, @RequestParam("password") String password,
			@RequestParam("userImage") MultipartFile userImage) {

		UserModel e_model = vRepository.findByUserId(userId);

		logger.info("UPDATE USER ID : " + userId);
		logger.info("UPDATE EMAIL : " + email);
		logger.info("UPDATE PASSWORD : " + password);

		UserLoginResponse response = new UserLoginResponse();

		if (e_model == null) {
			response.setStatus("0");
			response.setMessage("Invalid User ID");
			return new ResponseEntity<UserLoginResponse>(response, HttpStatus.OK);
		}
		String user_image_path = UploadImage.saveImage(path, userImage);
		e_model.setName(name);
		e_model.setEmail(email);
		e_model.setMobile(mobile);
		e_model.setPassword(password);
		e_model.setUserImage(user_image_path);

		UserModel data = uService.updateUser(e_model);
		response.setStatus("1");
		response.setMessage("Updated Successfully");
		UserLoginDto dto = this.modelMapper.map(data, UserLoginDto.class);
		response.setUser_data(dto);
		return new ResponseEntity<UserLoginResponse>(response, HttpStatus.OK);
	}

	// DELTE SINGLE USER
	@PostMapping("/delete_user")
	public ResponseEntity<CommonResponse> deleteSingleUser(@RequestParam("userId") String userId) {
		UserModel e_model = vRepository.findByUserId(userId);

		logger.info("DELETE USER ID : " + userId);
		CommonResponse response = new CommonResponse();
		if (e_model == null) {
			response.status = "0";
			response.message = "Invalid User Id";
			return new ResponseEntity<CommonResponse>(response, HttpStatus.OK);
		}

		uService.deleteUser(e_model);
		response.status = "1";
		response.message = "User Deleted Successfully";
		return new ResponseEntity<CommonResponse>(response, HttpStatus.OK);

	}

	// UPDATE FIREBASE TOKEN
	@PostMapping("/update_user_firebase_token")
	public ResponseEntity<CommonResponse> updateUserFirebaseToken(@RequestParam("userId") String userId,
			@RequestParam("firebaseToken") String firebaseToken) {

		UserModel e_model = vRepository.findByUserId(userId);

		logger.info("UPDATE USER FIREBASE TOKEN ID : " + userId);

		CommonResponse response = new CommonResponse();

		if (e_model == null) {
			response.setStatus("0");
			response.setMessage("Invalid User ID");
			return new ResponseEntity<CommonResponse>(response, HttpStatus.OK);
		}

		e_model.setFirebaseToken(firebaseToken);

		uService.updateUser(e_model);
		response.setStatus("1");
		response.setMessage("Token Updated Successfully");
		return new ResponseEntity<CommonResponse>(response, HttpStatus.OK);
	}

	// GET SINGLE USER DETAILS
	@PostMapping("/get_user_details")
	public ResponseEntity<UserLoginResponse> getUserDetails(@RequestParam("userId") String userId) {

		UserModel e_model = vRepository.findByUserId(userId);
		logger.info("GET USER DETAILS USER ID : " + userId);
		UserLoginResponse response = new UserLoginResponse();

		if (e_model == null) {
			response.setStatus("0");
			response.setMessage("Invalid User ID");
			return new ResponseEntity<UserLoginResponse>(response, HttpStatus.OK);
		}
		response.setStatus("1");
		response.setMessage("Single User Data ");
		UserLoginDto dto = this.modelMapper.map(e_model, UserLoginDto.class);
		response.setUser_data(dto);
		return new ResponseEntity<UserLoginResponse>(response, HttpStatus.OK);
	}

	// GET ALL USERS DATA
	@GetMapping("/users_list")
	public ResponseEntity<UserListResponse> getAllUsersList() {
		List<UserLoginDto> list = uService.getAllUsers();
		UserListResponse response = new UserListResponse();
		response.setStatus("1");
		response.setMessage("Users List");
		response.setList(list);
		return new ResponseEntity<UserListResponse>(response, HttpStatus.OK);
	}
	
	//ACTIVE USER
	@PostMapping("/active_user")
	public ResponseEntity<CommonResponse> activeUser(@RequestParam("userId") String userId) {

		UserModel e_model = vRepository.findByUserId(userId);

		logger.info("ACTIVE USER USER ID : " + userId);

		CommonResponse response = new CommonResponse();

		if (e_model == null) {
			response.setStatus("0");
			response.setMessage("Invalid User ID");
			return new ResponseEntity<CommonResponse>(response, HttpStatus.OK);
		}

		e_model.setApproveStatus("1");

		uService.updateUser(e_model);
		response.setStatus("1");
		response.setMessage("Status Updated Successfully");
		return new ResponseEntity<CommonResponse>(response, HttpStatus.OK);
	}
	
	//DE-ACTIVE USER
	@PostMapping("/deactive_user")
	public ResponseEntity<CommonResponse> deActiveUser(@RequestParam("userId") String userId) {

		UserModel e_model = vRepository.findByUserId(userId);

		logger.info("ACTIVE USER USER ID : " + userId);

		CommonResponse response = new CommonResponse();

		if (e_model == null) {
			response.setStatus("0");
			response.setMessage("Invalid User ID");
			return new ResponseEntity<CommonResponse>(response, HttpStatus.OK);
		}

		e_model.setApproveStatus("0");

		uService.updateUser(e_model);
		response.setStatus("1");
		response.setMessage("Status Updated Successfully");
		return new ResponseEntity<CommonResponse>(response, HttpStatus.OK);
	}
	

	

}
