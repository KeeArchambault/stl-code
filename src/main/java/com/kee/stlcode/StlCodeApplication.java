package com.kee.stlcode;

import com.kee.stlcode.controllers.UploadController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.io.File;

@SpringBootApplication
@ComponentScan({"com.kee.stlcode", "UploadController"})
public class StlCodeApplication {

	public static void main(String[] args){
		new File(UploadController.uploadDirectory).mkdir();
		SpringApplication.run(StlCodeApplication.class, args);
	}
}
