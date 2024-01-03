package com.mee.ride.user.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mee.ride.user.model.UserOrderModel;
import com.mee.ride.user.repository.OrderRepository;
import com.mee.ride.user.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private ModelMapper modelMapper;
	private OrderRepository orderRepository;
	
	
	
	public OrderServiceImpl(OrderRepository orderRepository) {
		super();
		this.orderRepository = orderRepository;
	}



	@Override
	public UserOrderModel placeOrder(UserOrderModel uModel) {
		// TODO Auto-generated method stub
		return orderRepository.save(uModel);
	}



	@Override
	public UserOrderModel updateOrder(UserOrderModel uModel) {
		// TODO Auto-generated method stub
		return orderRepository.save(uModel);
	}



	@Override
	public List<UserOrderModel> getAllUserOrders() {
		// TODO Auto-generated method stub
		return orderRepository.findAll();
	}

}
