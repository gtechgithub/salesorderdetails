package com.storeorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.storeorder.service.StoreOrderService;

@EnableJpaRepositories(basePackages = {"com.storeorder.repository"})
@SpringBootApplication
public class StoreOrderApplication implements CommandLineRunner{

	@Autowired
	StoreOrderService orderService;
	
	public static void main(String[] args) {
		SpringApplication.run(StoreOrderApplication.class, args);
	}
	
	@Override
	public void run(String ... args) {
		orderService.saveSalesRecords();

	}
}
