package com.mee.ride.admin.responses;

import java.util.List;

import com.mee.ride.admin.model.AdminModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AllAdminsResponse {
	private String status;
	private String message;
	List<AdminModel> list ;

}
