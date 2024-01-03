package com.mee.ride.rider.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

public class UploadImage {
	
	

	public static String saveImage(String path, MultipartFile file) {
		
		//FILE NAME
		String name = file.getOriginalFilename();
		
		//FULL PATH
		String randomString = UUID.randomUUID().toString();
		String fileName = randomString.concat(name.substring(name.lastIndexOf(".")));
		String filePath = path+File.separator+fileName;
		System.out.println("FILE PATH : "+filePath);
		
		//CREATE FOLDER IF NOT CREATED
		File f = new File(path);
		if (!f.exists()) {
			f.mkdir();
		}
		
		//COPY FILE TO FOLDER
		try {
			Files.copy(file.getInputStream(), Paths.get(filePath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return fileName;
	}
}
