package com.mee.ride.user.model;

import java.util.Map;

import lombok.Data;

@Data
public class Note {
	private String title;
    private String body;
    private Map<String, String> data;
    private String image;

}
