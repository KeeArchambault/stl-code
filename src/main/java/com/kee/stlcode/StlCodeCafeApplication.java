package com.kee.stlcode;

import com.kee.stlcode.controllers.UploadController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.io.File;

@SpringBootApplication
public class StlCodeCafeApplication {
	public static void main(String[] args){
		SpringApplication.run(StlCodeCafeApplication.class, args);
	}

}
