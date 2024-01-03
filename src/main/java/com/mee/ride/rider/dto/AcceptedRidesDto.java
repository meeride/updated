package com.mee.ride.rider.dto;

import lombok.Data;

@Data
public class AcceptedRidesDto {
	private String orderId;
    private String userId;
    private String riderId;
    private String pickAddress;
    private String dropAddress;
    private double pickLat ;
    private double pickLon;
    private double dropLat ;
    private double dropLon;
    private double km;
    private double totalAmount;
    private String paymentType;
    private String orderStatus;
    private String orderDate;
    private String pickTime;
    private String dropTime;
    private String duration;
    private String vehicleTypeId;
    private int otp;
}
