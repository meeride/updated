package com.mee.ride.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mee.ride.user.model.UserOrderModel;

public interface OrderRepository extends JpaRepository<UserOrderModel, Long> {
	UserOrderModel findByOrderId(String orderId);
	UserOrderModel findByOrderIdAndOrderStatus(String orderId, String orderStatus);
	List<UserOrderModel> findAllByRiderIdAndOrderStatus(String riderId, String orderStatus);
	List<UserOrderModel> findAllByRiderId(String riderId);
	List<UserOrderModel> findAllByUserId(String userId);
	List<UserOrderModel> findAllByOrderStatus(String orderStatus);

	
}
