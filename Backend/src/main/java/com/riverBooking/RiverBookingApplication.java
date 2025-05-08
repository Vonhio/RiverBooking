package com.riverBooking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class RiverBookingApplication {

	public static void main(String[] args) {
		SpringApplication.run(RiverBookingApplication.class, args);
	}

}
