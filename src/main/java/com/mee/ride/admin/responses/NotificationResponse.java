package com.mee.ride.admin.responses;

import java.util.List;

import com.mee.ride.admin.model.NotificationModel;

import lombok.Data;

@Data
public class NotificationResponse {
	private String status;
	private String message;
	private List<NotificationModel> list;
}
