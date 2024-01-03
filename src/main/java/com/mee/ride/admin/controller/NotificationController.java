package com.mee.ride.admin.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.mee.ride.admin.model.NotificationModel;
import com.mee.ride.admin.repository.NotificationRepository;
import com.mee.ride.admin.responses.NotificationResponse;
import com.mee.ride.admin.service.NotificationService;
import com.mee.ride.rider.model.RegisterRider;
import com.mee.ride.rider.repository.RiderRegisterRepository;
import com.mee.ride.rider.responses.CommonResponse;
import com.mee.ride.rider.service.MyFirebaseMessagingService;
import com.mee.ride.rider.service.RiderRegisterService;
import com.mee.ride.rider.utils.UploadImage;
import com.mee.ride.user.dto.UserLoginDto;
import com.mee.ride.user.model.BannerModel;
import com.mee.ride.user.model.Note;
import com.mee.ride.user.model.UserModel;
import com.mee.ride.user.repository.UserRepository;
import com.mee.ride.user.service.UserService;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {

	Logger logger = LoggerFactory.getLogger(NotificationController.class);

	@Value("${project.image}")
	private String path;

	NotificationRepository notificationRepository;
	NotificationService notificationService;
	private RiderRegisterService riderRegisterService;
	private RiderRegisterRepository riderRegisterRepository;
	private UserService userService;
	private UserRepository userRepository;
	private MyFirebaseMessagingService firebaseMessagingService;

	public NotificationController(NotificationRepository notificationRepository,
			NotificationService notificationService, RiderRegisterService riderRegisterService,
			RiderRegisterRepository riderRegisterRepository, UserService userService, UserRepository userRepository,
			MyFirebaseMessagingService firebaseMessagingService) {
		super();
		this.notificationRepository = notificationRepository;
		this.notificationService = notificationService;
		this.riderRegisterService = riderRegisterService;
		this.riderRegisterRepository = riderRegisterRepository;
		this.userService = userService;
		this.userRepository = userRepository;
		this.firebaseMessagingService = firebaseMessagingService;
	}

	// CREATE NOTIFICATION
	@PostMapping("/create_user_notification")
	public ResponseEntity<CommonResponse> createUserNotification(@RequestParam("title") String title,
			@RequestParam("body") String body, @RequestParam("image") MultipartFile image)
			throws FirebaseMessagingException {

		List<UserModel> list = userRepository.findAll();

		for (int i = 0; i < list.size(); i++) {

			logger.info("USER FIREBASE TOKEN : " + list.get(i).getFirebaseToken());

			if (list.get(i).getFirebaseToken() != null) {

				// SEND NOTIFICATION TO USER DEVICE
				Note note = new Note();
				note.setTitle(title);
				note.setBody(body);
				note.setImage("https://cdn.motor1.com/images/mgl/ZnmO23/s3/future-cars-2023-2026.jpg");
				firebaseMessagingService.sendNotification(note, list.get(i).getFirebaseToken());

				// SAVE NOTIFICATION IN DATABASE
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				Date date = new Date();
				String current_date = formatter.format(date);

				String notification_image_path = UploadImage.saveImage(path, image);
				NotificationModel model = new NotificationModel();
				model.setType("USER");
				model.setTitle(title);
				model.setBody(body);
				// model.setImage(notification_image_path);
				model.setImage("https://cdn.motor1.com/images/mgl/ZnmO23/s3/future-cars-2023-2026.jpg");
				model.setDate(current_date);
				model.setUserId(list.get(i).getUserId());

				notificationService.createNotification(model);

			}

		}

		CommonResponse response = new CommonResponse();
		response.status = "1";
		response.message = "Notification Sent Successfully";
		return new ResponseEntity<CommonResponse>(response, HttpStatus.OK);
	}

	// CREATE NOTIFICATION
	@PostMapping("/create_rider_notification")
	public ResponseEntity<CommonResponse> createRiderNotification(@RequestParam("title") String title,
			@RequestParam("body") String body, @RequestParam("image") MultipartFile image)
			throws FirebaseMessagingException {

		List<RegisterRider> list = riderRegisterRepository.findAll();

		for (int i = 0; i < list.size(); i++) {

			logger.info("USER FIREBASE TOKEN : " + list.get(i).getFirebase_token());

			if (list.get(i).getFirebase_token() != null) {

				// SEND NOTIFICATION TO Rider DEVICE
				Note note = new Note();
				note.setTitle(title);
				note.setBody(body);
				note.setImage("https://cdn.motor1.com/images/mgl/ZnmO23/s3/future-cars-2023-2026.jpg");
				firebaseMessagingService.sendNotification(note, list.get(i).getFirebase_token());

				// SAVE NOTIFICATION IN DATABASE
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				Date date = new Date();
				String current_date = formatter.format(date);

				String notification_image_path = UploadImage.saveImage(path, image);
				NotificationModel model = new NotificationModel();
				model.setType("RIDER");
				model.setTitle(title);
				model.setBody(body);
				// model.setImage(notification_image_path);
				model.setImage("https://cdn.motor1.com/images/mgl/ZnmO23/s3/future-cars-2023-2026.jpg");
				model.setDate(current_date);
				model.setUserId(list.get(i).getRiderId());

				notificationService.createNotification(model);

			}

		}

		CommonResponse response = new CommonResponse();
		response.status = "1";
		response.message = "Notification Sent Successfully";
		return new ResponseEntity<CommonResponse>(response, HttpStatus.OK);
	}
	
	//GET ALL NOTIFICATIONS
	@GetMapping("/all_notifications")
	public ResponseEntity<NotificationResponse> getAllNotifications(){
		
		List<NotificationModel> list = notificationRepository.findAll();
		NotificationResponse response = new NotificationResponse();
		if (list == null) {
			response.setStatus("0");
			response.setMessage("No Notification Found");
			return new ResponseEntity<NotificationResponse>(response, HttpStatus.OK);
		}
		
		response.setStatus("1");
		response.setMessage("Notifications");
		response.setList(list);
		return new ResponseEntity<NotificationResponse>(response, HttpStatus.OK);
		
	}
	
	//GET SINGLE USER NOTIFICATIONS
	@PostMapping("/user_notification")
	public ResponseEntity<NotificationResponse> getSingleUserNotification(@RequestParam("user_id") String user_id){
		List<NotificationModel> list = notificationRepository.findAllByTypeAndUserId("USER", user_id);
		NotificationResponse response = new NotificationResponse();
		if (list == null) {
			response.setStatus("0");
			response.setMessage("No Notification Found");
			return new ResponseEntity<NotificationResponse>(response, HttpStatus.OK);
		}
		
		response.setStatus("1");
		response.setMessage("Notifications");
		response.setList(list);
		return new ResponseEntity<NotificationResponse>(response, HttpStatus.OK);
	}
	
	//GET SINGLE RIDER NOTIFICATIONS
		@PostMapping("/rider_notification")
		public ResponseEntity<NotificationResponse> getSingleRiderNotification(@RequestParam("rider_id") String rider_id){
			List<NotificationModel> list = notificationRepository.findAllByTypeAndUserId("RIDER", rider_id);
			NotificationResponse response = new NotificationResponse();
			if (list == null) {
				response.setStatus("0");
				response.setMessage("No Notification Found");
				return new ResponseEntity<NotificationResponse>(response, HttpStatus.OK);
			}
			
			response.setStatus("1");
			response.setMessage("Notifications");
			response.setList(list);
			return new ResponseEntity<NotificationResponse>(response, HttpStatus.OK);
		}

}
