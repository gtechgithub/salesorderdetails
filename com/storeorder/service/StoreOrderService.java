package com.storeorder.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.storeorder.entity.StoreOrder;
import com.storeorder.exception.SalesException;
import com.storeorder.models.StoreOrderDetails;
import com.storeorder.repository.StoreOrderRepository;
import com.storeorder.util.csvStoreOrderReader;

@Service
public class StoreOrderService {

	@Autowired
	StoreOrderRepository storeRepo;

	@Autowired
	csvStoreOrderReader csvSalesReader;

	public StoreOrder saveRecords(StoreOrder storeOrder) {
		return storeRepo.save(storeOrder);
	}

	public List<StoreOrder> saveAllRecords(List<StoreOrder> storeOrder) {
		return storeRepo.saveAll(storeOrder);
	}

	public void saveSalesRecords() {
		ModelMapper modelMapper = new ModelMapper();
		List<StoreOrderDetails> salesDetails = null;

		try {
			csvSalesReader.parseCSV();
			salesDetails = csvSalesReader.getSalesDetailList();

			salesDetails.stream().filter(Objects::nonNull).forEach(salesDetail -> {
				StoreOrder storeOrder = modelMapper.map(salesDetail, StoreOrder.class);
				
				System.out.println("salesDetail:" + salesDetail);
				
				System.out.println("storeOrder:" + storeOrder);
				
				try {
				storeRepo.save(storeOrder);
				}catch(DataIntegrityViolationException ex) {
					System.out.println("exception message:" + ex.getMessage());
				}
			});
		} catch (FileNotFoundException e) {
			System.out.println("exception message:" + e.getMessage());
		} catch (IOException e) {
			System.out.println("exception message:" + e.getMessage());
		} catch (SalesException s) {
			System.out.println("exception message:" + s.getMessage());
		}
	}
}
