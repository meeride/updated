package com.mee.ride.rider.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mee.ride.rider.dto.RiderReviewDto;
import com.mee.ride.rider.model.RegisterRider;
import com.mee.ride.rider.model.RiderReview;
import com.mee.ride.rider.model.Vehicles;
import com.mee.ride.rider.repository.RiderRegisterRepository;
import com.mee.ride.rider.repository.RiderReviewRepository;
import com.mee.ride.rider.responses.CommonResponse;
import com.mee.ride.rider.responses.FirebaseTokenUpdateResponse;
import com.mee.ride.rider.responses.OrderReviewResponse;
import com.mee.ride.rider.responses.RiderReviewResponse;
import com.mee.ride.rider.service.RiderReviewService;
import com.mee.ride.rider.utils.CalculateDistance;
import com.mee.ride.rider.utils.UploadImage;
import com.mee.ride.user.model.UserModel;
import com.mee.ride.user.repository.UserRepository;

@RestController
@RequestMapping("/api/rider/review")
public class RiderReviewController {

	Logger logger = LoggerFactory.getLogger(RiderReviewController.class);
	private ModelMapper modelMapper;

	RiderRegisterRepository riderRegisterRepository;
	RiderReviewRepository riderReviewRepository;
	UserRepository userRepository;
	RiderReviewService reviewService;

	public RiderReviewController(RiderRegisterRepository riderRegisterRepository,
			RiderReviewRepository riderReviewRepository, UserRepository userRepository,
			RiderReviewService reviewService) {
		super();
		this.riderRegisterRepository = riderRegisterRepository;
		this.riderReviewRepository = riderReviewRepository;
		this.userRepository = userRepository;
		this.reviewService = reviewService;
	}

	// ADD REVIEW
	@PostMapping("/add_review")
	public ResponseEntity<CommonResponse> registerUser(@RequestParam("rider_id") String rider_id,
			@RequestParam("user_id") String user_id, @RequestParam("stars") String stars,
			@RequestParam("review") String review, @RequestParam("order_id") String order_id) {

		RiderReview e_rReview = riderReviewRepository.findByOrderId(order_id);

		CommonResponse response = new CommonResponse();

		if (e_rReview != null) {
			response.setStatus("0");
			response.setMessage("Review Added Already");
			return new ResponseEntity<CommonResponse>(response, HttpStatus.OK);
		}

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();
		String current_date = formatter.format(date);

		RiderReview modelReview = new RiderReview();
		modelReview.setRiderId(rider_id);
		modelReview.setUserId(user_id);
		modelReview.setOrderId(order_id);
		modelReview.setReview(review);
		modelReview.setReviewDate(current_date);
		modelReview.setStars(stars);
		reviewService.addReview(modelReview);

		response.setStatus("1");
		response.setMessage("Review Added Sucessfully");

		return new ResponseEntity<CommonResponse>(response, HttpStatus.OK);
	}

	// GET RIDER REVIEW
	@PostMapping("/get_review")
	public ResponseEntity<RiderReviewResponse> getRiderReview(@RequestParam("rider_id") String rider_id) {

		RegisterRider e_rider = riderRegisterRepository.findByRiderId(rider_id);

		logger.info("GET REVIEW RIDER_ID : " + rider_id);

		RiderReviewResponse response = new RiderReviewResponse();
		if (e_rider == null) {
			response.setStatus("0");
			response.setMessage("Wrong Rider Id");
			return new ResponseEntity<RiderReviewResponse>(response, HttpStatus.OK);
		}

		List<RiderReview> list = riderReviewRepository.findAllByRiderId(rider_id);
		logger.info("GET REVIEW LIST : " + list.size());

		if (list.size() == 0) {
			response.setStatus("0");
			response.setMessage("No Reviews");
			return new ResponseEntity<RiderReviewResponse>(response, HttpStatus.OK);
		}

		List<RiderReviewDto> review_list = new ArrayList<>();

		for (int i = 0; i < list.size(); i++) {
			RiderReviewDto dto = new RiderReviewDto();
			dto.setID(list.get(i).getID());
			dto.setOrderId(list.get(i).getOrderId());
			dto.setReview(list.get(i).getReview());
			dto.setReviewDate(list.get(i).getReviewDate());
			dto.setRiderId(rider_id);
			dto.setRiderName(e_rider.getName());
			dto.setStars(list.get(i).getStars());
			dto.setUserId(list.get(i).getUserId());
			UserModel uModel = userRepository.findByUserId(list.get(i).getUserId());
			dto.setUserName(uModel.getName());
			dto.setUserImage(uModel.getUserImage());
			review_list.add(dto);

		}

		response.setStatus("1");
		response.setMessage("Review List");
		response.setList(review_list);

		return new ResponseEntity<RiderReviewResponse>(response, HttpStatus.OK);

	}

	// GET REVIEW BY ORDER ID
	@PostMapping("/get_order_review")
	public ResponseEntity<OrderReviewResponse> getOrderReview(@RequestParam("order_id") String order_id) {

		logger.info("GET ORDER REVIEW RIDER_ID : " + order_id);
		RiderReview review = riderReviewRepository.findByOrderId(order_id);

		OrderReviewResponse response = new OrderReviewResponse();

		if (review == null) {
			response.setStatus("0");
			response.setMessage("No Reviews Found");
			return new ResponseEntity<OrderReviewResponse>(response, HttpStatus.OK);
		}

		RegisterRider e_rider = riderRegisterRepository.findByRiderId(review.getRiderId());

		RiderReviewDto dto = new RiderReviewDto();
		dto.setID(review.getID());
		dto.setOrderId(review.getOrderId());
		dto.setReview(review.getReview());
		dto.setReviewDate(review.getReviewDate());
		dto.setRiderId(review.getRiderId());
		dto.setRiderName(e_rider.getName());
		dto.setStars(review.getStars());
		dto.setUserId(review.getUserId());
		UserModel uModel = userRepository.findByUserId(review.getUserId());
		dto.setUserName(uModel.getName());
		dto.setUserImage(uModel.getUserImage());

		// CMR - 17.739134433631033, 83.3180756201794
		// PARWADA - 17.6417373747305, 83.09275427441662
		// double dist = CalculateDistance.distFrom(17.739134433631033,
		// 83.3180756201794, 17.6417373747305, 83.09275427441662);

		response.setStatus("1");
		response.setMessage("Order Review ");
		response.setReview(dto);

		return new ResponseEntity<OrderReviewResponse>(response, HttpStatus.OK);

	}

}
