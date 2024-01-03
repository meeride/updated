
package com.mee.ride.user.controller;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.lowagie.text.DocumentException;
import com.mee.ride.rider.dto.AcceptedRidesDto;
import com.mee.ride.rider.dto.RegisterDto;
import com.mee.ride.rider.model.RegisterRider;
import com.mee.ride.rider.model.RiderWallet;
import com.mee.ride.rider.repository.RiderRegisterRepository;
import com.mee.ride.rider.repository.WalletRepository;
import com.mee.ride.rider.responses.CommonResponse;
import com.mee.ride.rider.responses.RiderProfileResponse;
import com.mee.ride.rider.responses.RidesCountResponse;
import com.mee.ride.rider.responses.RidesListResponse;
import com.mee.ride.rider.responses.SingleRideResponse;
import com.mee.ride.rider.service.MyFirebaseMessagingService;
import com.mee.ride.rider.service.RiderRegisterService;
import com.mee.ride.rider.service.RiderWalletService;
import com.mee.ride.rider.utils.PdfGenerator;
import com.mee.ride.rider.utils.UploadImage;
import com.mee.ride.user.dto.UserLoginDto;
import com.mee.ride.user.model.BannerModel;
import com.mee.ride.user.model.Note;
import com.mee.ride.user.model.UserModel;
import com.mee.ride.user.model.UserOrderModel;
import com.mee.ride.user.model.UserWallet;
import com.mee.ride.user.repository.OrderRepository;
import com.mee.ride.user.repository.UserRepository;
import com.mee.ride.user.responses.BannerResponse;
import com.mee.ride.user.responses.UserOrderResponse;
import com.mee.ride.user.responses.UserRegisterResponse;
import com.mee.ride.user.service.OrderService;
import com.mee.ride.user.service.UserService;
import com.mee.ride.user.service.UserWalletService;

import jakarta.servlet.http.HttpServletResponse;
import java.text.DateFormat;


@RestController
@RequestMapping("/api/order")
public class OrderController {
	Logger logger = LoggerFactory.getLogger(OrderController.class);

	@Value("${project.image}")
	private String path;

	@Autowired
	private ModelMapper modelMapper;

	private OrderService orderService;
	private OrderRepository orderRepository;
	private WalletRepository wRepository;
	private RiderWalletService wService;
	private RiderRegisterService riderRegisterService;
	private RiderRegisterRepository riderRegisterRepository;
	private UserService uService;
	private UserRepository vRepository;
	private UserWalletService userWalletService;
	private final MyFirebaseMessagingService firebaseService;

	public OrderController(OrderService orderService, OrderRepository orderRepository, WalletRepository wRepository,
			RiderWalletService wService, RiderRegisterService riderRegisterService,
			RiderRegisterRepository riderRegisterRepository, UserService uService, UserRepository vRepository,
			UserWalletService userWalletService,MyFirebaseMessagingService firebaseService
			) {
		super();
		this.orderService = orderService;
		this.orderRepository = orderRepository;
		this.wRepository = wRepository;
		this.wService = wService;
		this.riderRegisterService = riderRegisterService;
		this.riderRegisterRepository = riderRegisterRepository;
		this.uService = uService;
		this.vRepository = vRepository;
		this.userWalletService = userWalletService;
		this.firebaseService = firebaseService;
	}

	// CREATE ORDER
	@PostMapping("/place_order")
	public ResponseEntity<UserOrderResponse> placeOrder(@RequestParam("userId") String userId,
			@RequestParam("pickAddress") String pickAddress, @RequestParam("dropAddress") String dropAddress,
			@RequestParam("pickLat") Double pickLat, @RequestParam("pickLon") Double pickLon,
			@RequestParam("dropLat") Double dropLat, @RequestParam("dropLon") Double dropLon,
			@RequestParam("distance") Double km, @RequestParam("totalAmount") Double totalAmount,
			@RequestParam("couponId") String couponId, @RequestParam("couponAmount") Double couponAmount,
			
			@RequestParam("paymentType") String paymentType, @RequestParam("transactionId") String transactionId,
			@RequestParam("vehicleTypeId") String vehicleTypeId, @RequestParam("duration") String duration) {

		logger.info("ORDER USER ID : " + userId);
		UserOrderResponse response = new UserOrderResponse();

		int min = 10000;
		int max = 99999;
		int random_number = (int) (Math.random() * (max - min + 1) + min);
		String order_id = "ORDER_" + random_number;

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();
		String current_date = formatter.format(date);

		int otp_min = 111111;
		int otp_max = 999999;
		int otp = (int) (Math.random() * (otp_max - otp_min + 1) + otp_min);

		UserOrderModel uModel = new UserOrderModel();
		uModel.setOrderId(order_id);
		uModel.setUserId(userId);
		uModel.setPickAddress(pickAddress);
		uModel.setDropAddress(dropAddress);
		uModel.setPickLat(pickLat);
		uModel.setPickLon(pickLon);
		uModel.setDropLat(dropLat);
		uModel.setDropLon(dropLon);
		uModel.setKm(km);
		uModel.setTotalAmount(totalAmount);
		uModel.setCouponId(couponId);
		uModel.setCouponAmount(couponAmount);
		uModel.setPaymentType(paymentType);
		uModel.setTransactionId(transactionId);
		uModel.setVehicleTypeId(vehicleTypeId);
		uModel.setOrderDate(current_date);
		uModel.setOrderStatus("Pending");
		uModel.setOtp(otp);
		uModel.setRiderCommission(0.0);
		uModel.setIsReviewed("0");
		uModel.setDuration(duration);
		
		/*
		 * if (walletAmount!=0) { UserWallet wallet = new UserWallet();
		 * wallet.setUserId(userId); wallet.setType("DEBIT");
		 * wallet.setTimeStamp(current_date); wallet.setAmount(walletAmount);
		 * wallet.setTransactionId("ORDER"); userWalletService.addToWallet(wallet); }
		 */
		
		//SEND NOTIFICATION TO RIDERS
		List<RegisterDto> list = riderRegisterService.getAllRiders();
		for (int i = 0; i < list.size(); i++) {
			String token = list.get(i).getFirebase_token();
			logger.info("ORDER TOKEN : " + token);
			if (token.isBlank()) {
				
				
			}else {
				Note note = new Note();
				note.setTitle("New Ride");
				note.setBody("You Have a ride");
				Map<String, String> map = new HashMap<>();
				map.put("order_id", order_id);
				note.setData(map);
				
				try {
					firebaseService.sendNotification(note, token);
				} catch (FirebaseMessagingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		}
		
		
		
		

		UserOrderModel responModel = orderService.placeOrder(uModel);

		response.setStatus("1");
		response.setMessage("Order Placed Successfully");
		response.setOrder_id(responModel.getOrderId());
		return new ResponseEntity<UserOrderResponse>(response, HttpStatus.OK);
	}

	// GET ALL ACCEPTED ORDERS
	@PostMapping("/all_pending_rides")
	public ResponseEntity<RidesListResponse> allPendingRides(@RequestParam("riderId") String riderId) {
		logger.info("ALL PENDING ORDERS RIDER ID : " + riderId);

		List<UserOrderModel> uModel = orderRepository.findAllByRiderIdAndOrderStatus(riderId, "Pending");
		RidesListResponse response = new RidesListResponse();

		if (uModel == null) {
			response.setStatus("0");
			response.setMessage("No Rides Found");
			return new ResponseEntity<RidesListResponse>(response, HttpStatus.OK);
		}

		List<AcceptedRidesDto> rides = uModel.stream().map((uid) -> this.modelMapper.map(uid, AcceptedRidesDto.class))
				.collect(Collectors.toList());

		response.setStatus("1");
		response.setMessage("Pending Rides");
		response.setRides(rides);

		return new ResponseEntity<RidesListResponse>(response, HttpStatus.OK);
	}

	// GET ALL ACCEPTED ORDERS
	@PostMapping("/all_accepted_rides")
	public ResponseEntity<RidesListResponse> allAcceptedRides(@RequestParam("riderId") String riderId) {
		logger.info("ALL ACCEPTED ORDERS RIDER ID : " + riderId);

		List<UserOrderModel> uModel = orderRepository.findAllByRiderIdAndOrderStatus(riderId, "Accepted");
		RidesListResponse response = new RidesListResponse();

		if (uModel == null) {
			response.setStatus("0");
			response.setMessage("No Rides Found");
			return new ResponseEntity<RidesListResponse>(response, HttpStatus.OK);
		}

		List<AcceptedRidesDto> rides = uModel.stream().map((uid) -> this.modelMapper.map(uid, AcceptedRidesDto.class))
				.collect(Collectors.toList());

		response.setStatus("1");
		response.setMessage("Accepted Rides");
		response.setRides(rides);

		return new ResponseEntity<RidesListResponse>(response, HttpStatus.OK);
	}
	
	// GET SINGLE ACCEPTED ORDER
	@PostMapping("/single_pending_ride")
	public ResponseEntity<SingleRideResponse> singlePendingRide(@RequestParam("orderId") String orderId) {
		logger.info("SINGLE PENDING ORDERS ORDER ID : " + orderId);

		UserOrderModel uModel = orderRepository.findByOrderIdAndOrderStatus(orderId, "Pending");
		SingleRideResponse response = new SingleRideResponse();

		if (uModel == null) {
			response.setStatus("0");
			response.setMessage("Ride Assigned Already");
			return new ResponseEntity<SingleRideResponse>(response, HttpStatus.OK);
		}

		AcceptedRidesDto dto = this.modelMapper.map(uModel, AcceptedRidesDto.class);
		response.setStatus("1");
		response.setMessage("Pending Rides");
		response.setRides(dto);

		return new ResponseEntity<SingleRideResponse>(response, HttpStatus.OK);
	}

	// GET SINGLE ACCEPTED ORDER
	@PostMapping("/single_accepted_ride")
	public ResponseEntity<SingleRideResponse> singleAcceptedRide(@RequestParam("orderId") String orderId) {
		logger.info("ACCEPTED ORDERS ORDER ID : " + orderId);

		UserOrderModel uModel = orderRepository.findByOrderIdAndOrderStatus(orderId, "Accepted");
		SingleRideResponse response = new SingleRideResponse();

		if (uModel == null) {
			response.setStatus("0");
			response.setMessage("Invalid Rider ID");
			return new ResponseEntity<SingleRideResponse>(response, HttpStatus.OK);
		}

		AcceptedRidesDto dto = this.modelMapper.map(uModel, AcceptedRidesDto.class);
		response.setStatus("1");
		response.setMessage("Accepted Rides");
		response.setRides(dto);

		return new ResponseEntity<SingleRideResponse>(response, HttpStatus.OK);
	}

	// GET ALL ORDERS
	@PostMapping("/all_rides")
	public ResponseEntity<RidesListResponse> allRides(@RequestParam("riderId") String riderId) {
		logger.info("ALL ACCEPTED ORDERS RIDER ID : " + riderId);

		List<UserOrderModel> uModel = orderRepository.findAllByRiderId(riderId);
		RidesListResponse response = new RidesListResponse();

		if (uModel == null) {
			response.setStatus("0");
			response.setMessage("No Rides Found");
			return new ResponseEntity<RidesListResponse>(response, HttpStatus.OK);
		}

		List<AcceptedRidesDto> rides = uModel.stream().map((uid) -> this.modelMapper.map(uid, AcceptedRidesDto.class))
				.collect(Collectors.toList());

		response.setStatus("1");
		response.setMessage("All Rides");
		response.setRides(rides);

		return new ResponseEntity<RidesListResponse>(response, HttpStatus.OK);
	}

	// GET SINGLE ACCEPTED ORDER
	@PostMapping("/single_ride_details")
	public ResponseEntity<SingleRideResponse> singleRideDetails(@RequestParam("orderId") String orderId) {
		logger.info("ACCEPTED ORDERS ORDER ID : " + orderId);

		UserOrderModel uModel = orderRepository.findByOrderId(orderId);
		SingleRideResponse response = new SingleRideResponse();

		if (uModel == null) {
			response.setStatus("0");
			response.setMessage("Invalid Order ID");
			return new ResponseEntity<SingleRideResponse>(response, HttpStatus.OK);
		}

		AcceptedRidesDto dto = this.modelMapper.map(uModel, AcceptedRidesDto.class);
		response.setStatus("1");
		response.setMessage("Ride Details");
		response.setRides(dto);

		return new ResponseEntity<SingleRideResponse>(response, HttpStatus.OK);
	}
	
	//ACCEPT ORDER
	@PostMapping("/accept_ride")
	public ResponseEntity<CommonResponse> acceptRide(@RequestParam("orderId") String orderId,
			@RequestParam("riderId") String riderId) {
		logger.info("ACCEPT RIDE RIDER ID : " + riderId);
		UserOrderModel uModel = orderRepository.findByOrderId(orderId);
		CommonResponse response = new CommonResponse();
		if (uModel == null) {
			response.setStatus("0");
			response.setMessage("Invalid Order ID");
			return new ResponseEntity<CommonResponse>(response, HttpStatus.OK);
		}
		uModel.setRiderId(riderId);
		uModel.setOrderStatus("Accepted");
		
		
		orderService.updateOrder(uModel);
		response.setStatus("1");
		response.setMessage("Status Changed Successfully");

		return new ResponseEntity<CommonResponse>(response, HttpStatus.OK);
	}

	// CHANGE RIDE STATUS
	@PostMapping("/change_ride_status")
	public ResponseEntity<CommonResponse> changeRideStatus(@RequestParam("orderId") String orderId,
			@RequestParam("status") String status) {

		logger.info("CHANGE RIDE STATUS ORDER ID : " + orderId);
		logger.info("CHANGE RIDE STATUS STATUS : " + status);

		UserOrderModel uModel = orderRepository.findByOrderId(orderId);
		CommonResponse response = new CommonResponse();

		if (uModel == null) {
			response.setStatus("0");
			response.setMessage("Invalid Order ID");
			return new ResponseEntity<CommonResponse>(response, HttpStatus.OK);
		}
		
		

		uModel.setOrderStatus(status);

		if (status.equals("Reached")) {
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			String pickTime = sdf.format(cal.getTime());
			uModel.setPickTime(pickTime);

		}
		if (status.equals("Rejected")) {
			uModel.setCancelReason("Rejected By User");
		}
		if (status.equals("Completed")) {
			logger.info("COMPLETE RIDE RIDER ID : " + uModel.getRiderId());
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			String dropTime = sdf.format(cal.getTime());
			uModel.setDropTime(dropTime);

			// add money to wallet

			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			Date date = new Date();
			String current_date = formatter.format(date);

			RegisterRider e_rider = riderRegisterRepository.findByRiderId(uModel.getRiderId());
			//RegisterRider e_rider = riderRegisterRepository.findByRiderId("RIDER_90949");
			Double com = Double.parseDouble(e_rider.getComission());
			Double order_amount = uModel.getTotalAmount();

			Double p_amount = com / 100;
			Double s_amount = p_amount * order_amount;

			Double rider_commission = order_amount - s_amount;
			DecimalFormat df = new DecimalFormat("#.##");
			rider_commission = Double.parseDouble(df.format(rider_commission));

			logger.info("COMISSION AMOUNT : " + s_amount);
			logger.info("RIDER COMISSION AMOUNT : " + rider_commission);

			RiderWallet wallet = new RiderWallet();
			wallet.setOrderId(orderId);
			wallet.setRiderId(uModel.getRiderId());
			wallet.setType("CREDIT");
			wallet.setTimeStamp(current_date);
			wallet.setAmount(rider_commission);

			wService.addToWallet(wallet);

			// add rider amount to order table
			uModel.setRiderCommission(rider_commission);

		}

		orderService.updateOrder(uModel);

		response.setStatus("1");
		response.setMessage("Status Changed Successfully");

		return new ResponseEntity<CommonResponse>(response, HttpStatus.OK);
	}

	// VERIFY OTP AND START RIDE
	@PostMapping("/start_ride_with_otp")
	public ResponseEntity<CommonResponse> startRideWithOTP(@RequestParam("orderId") String orderId,
			@RequestParam("otp") int otp) {

		logger.info("START RIDE ORDER ID : " + orderId);
		logger.info("START RIDE OTP : " + otp);

		UserOrderModel uModel = orderRepository.findByOrderId(orderId);
		CommonResponse response = new CommonResponse();

		if (uModel == null) {
			response.setStatus("0");
			response.setMessage("Invalid Order ID");
			return new ResponseEntity<CommonResponse>(response, HttpStatus.OK);
		}

		if (uModel.getOtp() != otp) {
			response.setStatus("0");
			response.setMessage("Wrong OTP");
			return new ResponseEntity<CommonResponse>(response, HttpStatus.OK);
		}

		uModel.setOrderStatus("Started");
		orderService.updateOrder(uModel);

		response.setStatus("1");
		response.setMessage("Status Changed Successfully");

		return new ResponseEntity<CommonResponse>(response, HttpStatus.OK);
	}

	// RIDER PROFILE DATA
	@PostMapping("/rider_profile")
	public ResponseEntity<RiderProfileResponse> riderProfile(@RequestParam("rider_id") String riderId) {
		logger.info("RIDER PROFILE RIDER ID : " + riderId);

		List<UserOrderModel> uModel = orderRepository.findAllByRiderId(riderId);
		RiderProfileResponse response = new RiderProfileResponse();

		if (uModel == null) {
			response.setStatus("0");
			response.setMessage("No Rides Found");
			return new ResponseEntity<RiderProfileResponse>(response, HttpStatus.OK);
		}

		int total_rides = 0;
		double total_amount = 0.0;
		double total_km = 0.0;

		for (int i = 0; i < uModel.size(); i++) {
			if (uModel.get(i).getOrderStatus().equals("Completed")) {
				total_amount = total_rides + uModel.get(i).getRiderCommission();
				total_rides++;
				total_km = total_km + uModel.get(i).getKm();

			}
		}

		response.setStatus("1");
		response.setMessage("Rider Profile");
		response.setTotal_amount(total_amount);
		response.setTotal_km(total_km);
		response.setTotal_rides(total_rides);

		return new ResponseEntity<RiderProfileResponse>(response, HttpStatus.OK);
	}

	// ADMIN APIS
	// GET ALL USER ORDERS
	@GetMapping("/all_user_orders")
	public ResponseEntity<RidesListResponse> getAllUserOrders() {
		List<UserOrderModel> uModel = orderService.getAllUserOrders();
		RidesListResponse response = new RidesListResponse();

		if (uModel == null) {
			response.setStatus("0");
			response.setMessage("No Rides Found");
			return new ResponseEntity<RidesListResponse>(response, HttpStatus.OK);
		}

		List<AcceptedRidesDto> rides = uModel.stream().map((uid) -> this.modelMapper.map(uid, AcceptedRidesDto.class))
				.collect(Collectors.toList());

		response.setStatus("1");
		response.setMessage("All Rides");
		response.setRides(rides);

		return new ResponseEntity<RidesListResponse>(response, HttpStatus.OK);
	}
	
	//ORDERS COUNT
	@GetMapping("/orders_count")
	public ResponseEntity<RidesCountResponse> ordersCount() {
		
		
		List<UserOrderModel> acceptedModel = orderRepository.findAllByOrderStatus("Accepted");
		List<UserOrderModel> completedModel = orderRepository.findAllByOrderStatus("Completed");
		List<UserOrderModel> pendingModel = orderRepository.findAllByOrderStatus("Pending");
		List<UserOrderModel> cancelModel = orderRepository.findAllByOrderStatus("Rejected");
		

		RidesCountResponse response = new RidesCountResponse();
		response.setStatus("1");
		response.setMessage("Rides Count");
		


		if (pendingModel == null) {
			response.setPending("0");
		}else {
			response.setPending(""+pendingModel.size());
		}
		
		if (acceptedModel == null) {
			response.setAccepted("0");
		}else {
			response.setAccepted(""+acceptedModel.size());
		}
		
		if (completedModel == null) {
			response.setCompleted("0");
		}else {
			response.setCompleted(""+completedModel.size());
		}
		
		if (cancelModel == null) {
			response.setRejected("0");
		}else {
			response.setRejected(""+cancelModel.size());
		}
		

		
		

		return new ResponseEntity<RidesCountResponse>(response, HttpStatus.OK);
	}
	
	

	// USER APIS
	// GET SINGLE USER ORDERS
	@PostMapping("/my_rides")
	public ResponseEntity<RidesListResponse> singleUserOrders(@RequestParam("user_id") String user_id) {
		logger.info("SINGLE USER ORDERS ID : " + user_id);

		List<UserOrderModel> uModel = orderRepository.findAllByUserId(user_id);
		RidesListResponse response = new RidesListResponse();

		if (uModel == null) {
			response.setStatus("0");
			response.setMessage("No Rides");
			return new ResponseEntity<RidesListResponse>(response, HttpStatus.OK);
		}

		List<AcceptedRidesDto> rides = uModel.stream().map((uid) -> this.modelMapper.map(uid, AcceptedRidesDto.class))
				.collect(Collectors.toList());
		response.setStatus("1");
		response.setMessage("Ride Details");
		response.setRides(rides);

		return new ResponseEntity<RidesListResponse>(response, HttpStatus.OK);
	}
	
	//BILL DETAILS
	@GetMapping("/bill_receipt/{orderId}")
	public void billReceipt(@PathVariable("orderId") String orderId, HttpServletResponse response)throws DocumentException, IOException {
		logger.info("BILL RECEIPT ORDER ID : " + orderId);

		UserOrderModel uModel = orderRepository.findByOrderId(orderId);

		if (uModel != null) {
			
			response.setContentType("application/pdf");
		    DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD:HH:MM:SS");
		    String currentDateTime = dateFormat.format(new Date());
		    String headerkey = "Content-Disposition";
		    String headervalue = "attachment; filename=receipt" + currentDateTime + ".pdf";
		    response.setHeader(headerkey, headervalue);
		    PdfGenerator generator = new PdfGenerator();
		    generator.generate(uModel, response);
		}

		
	}

}
