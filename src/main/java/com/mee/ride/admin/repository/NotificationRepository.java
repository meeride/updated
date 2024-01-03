package com.mee.ride.admin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mee.ride.admin.controller.NotificationController;
import com.mee.ride.admin.model.NotificationModel;

public interface NotificationRepository extends JpaRepository<NotificationModel, Long> {
	
	List<NotificationModel> findAllByTypeAndUserId(String type, String user_id);

}
