package com.mee.ride.user.service;

import java.util.List;

import com.mee.ride.user.model.BannerModel;
import com.mee.ride.user.model.UserOrderModel;

public interface OrderService {

	UserOrderModel placeOrder(UserOrderModel uModel);
	UserOrderModel updateOrder(UserOrderModel uModel);
	List<UserOrderModel> getAllUserOrders();
	
}
