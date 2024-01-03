package com.mee.ride.admin.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mee.ride.admin.model.NotificationModel;
import com.mee.ride.admin.repository.NotificationRepository;
import com.mee.ride.admin.service.NotificationService;

@Service
public class NotificationServiceImpl implements NotificationService {

	private NotificationRepository repository;
	
	
	
	public NotificationServiceImpl(NotificationRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	public List<NotificationModel> getAllNotifications() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	@Override
	public void createNotification(NotificationModel notificationModel) {
		repository.save(notificationModel);

	}

}
