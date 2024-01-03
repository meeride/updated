package com.mee.ride.admin.service;

import java.util.List;

import com.mee.ride.admin.model.NotificationModel;



public interface NotificationService {
	List<NotificationModel> getAllNotifications();
	void createNotification(NotificationModel notificationModel);
}
