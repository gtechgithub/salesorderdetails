package com.storeorder.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

	@Value("${spring.jpa.properties.hibernate.jdbc.batch_size}")
	int batchSize;

	@Transactional
	public StoreOrder saveRecords(StoreOrder storeOrder) {
		return storeRepo.save(storeOrder);
	}

	@Transactional
	public void saveAllRecords(List<StoreOrder> storeOrder) {

		AtomicInteger batchCounter = new AtomicInteger(0);
		List<StoreOrder> storeOrderTemp = new ArrayList<>();

		Optional.ofNullable(storeOrder).ifPresent(storeOrd -> storeOrd.forEach(storeOr -> {
			storeOrderTemp.add(storeOr);

			if (batchCounter.incrementAndGet() % batchSize == 0 || (batchCounter.get() == storeOrd.size())) {
				try {
					storeRepo.saveAll(storeOrderTemp);
					storeOrderTemp.clear();

				} catch (DataIntegrityViolationException ex) {
					System.out.println("exception message:" + ex.getMessage());
				}
			}

		}));
	}

	public void saveSalesRecords() {
		ModelMapper modelMapper = new ModelMapper();
		List<StoreOrderDetails> salesDetails = null;

		try {
			csvSalesReader.parseCSV();
			salesDetails = csvSalesReader.getSalesDetailList();

//			salesDetails.stream().filter(Objects::nonNull).forEach(salesDetail -> {
//				
//				//filter the duplicate values using the jpa entity
//				StoreOrder storeOrder = modelMapper.map(salesDetail, StoreOrder.class);
//
//				//filter the duplicate values from the table load collections
//
//				//any changes in the existing object then update
//				System.out.println("salesDetail:" + salesDetail);
//				
//				System.out.println("storeOrder:" + storeOrder);
//				
//				try {
//				storeRepo.save(storeOrder);
//				}catch(DataIntegrityViolationException ex) {
//					System.out.println("exception message:" + ex.getMessage());
//				}
//			});

			System.out.println("inside line 1");

			// load from table by unique records of Order id, product id and customer id
			List<String> orderIds = storeRepo.findAllUniqueOrderIds();

			System.out.println("inside line 2");
			// load from table by unique records of Order id, product id and customer id
			List<String> productIds = storeRepo.findAllUniqueProductIds();

			System.out.println("inside line 3");
			// load from table by unique records of Order id, product id and customer id
			List<String> customerIds = storeRepo.findAllUniqueCustomerIds();

			// filter the duplicate values using the jpa entity
			// Type listType = new TypeToken<List<StoreOrder>>() {}.getType();
			// List<StoreOrder> storeOrderList = modelMapper.map(salesDetails, listType);

			System.out.println("inside line 4");
			List<StoreOrder> storeOrderList = new ArrayList<>();
			System.out.println("size:" + salesDetails.size());

			// include the records of unique records
			Optional.ofNullable(salesDetails).ifPresent(salesDetail -> salesDetail.forEach(salesDetai -> {
				if (null != salesDetai) {

					if ((orderIds == null || orderIds.size() == 0) && (productIds == null || productIds.size() == 0)
							&& (customerIds == null || customerIds.size() == 0)) {
						StoreOrder storeOrderTemp = modelMapper.map(salesDetai, StoreOrder.class);
						storeOrderList.add(storeOrderTemp);
						orderIds.add(storeOrderTemp.getOrderId());
						productIds.add(storeOrderTemp.getProductId());
						customerIds.add(storeOrderTemp.getCustomerID());

					} else if ((!orderIds.contains(salesDetai.getOrderId()))
							&& (!productIds.contains(salesDetai.getProductId()))
							&& (!customerIds.contains(salesDetai.getCustomerId()))) {

						StoreOrder storeOrderTemp = modelMapper.map(salesDetai, StoreOrder.class);
						storeOrderList.add(storeOrderTemp);
						orderIds.add(storeOrderTemp.getOrderId());
						productIds.add(storeOrderTemp.getProductId());
						customerIds.add(storeOrderTemp.getCustomerID());

					}

				}
			}));

			System.out.println("size:" + storeOrderList.size());
			// filter the duplicate values from the table load collect ions

//			Optional.ofNullable(storeOrderList).ifPresent(storeOrderLis -> storeOrderLis.forEach(storeOrderL -> {
//				if (null != storeOrderL && (null != productIds && productIds.size() > 0)) {
//
//					// the records are already available, insert will result in unique constraint
//					// hence needs to be ignored and removed from csv record list,
//					// just insert the record with unique values only
//					if (orderIds.contains(storeOrderL.getOrderId()) || productIds.contains(storeOrderL.getProductId())
//							|| customerIds.contains(storeOrderL.getCustomerID())) {
//						storeOrderLis.remove(storeOrderL);
//					}
//				}
//			}));

			// bulk insert logic
			saveAllRecords(storeOrderList);

		} catch (FileNotFoundException e) {
			System.out.println("exception message:" + e.getMessage());
		} catch (IOException e) {
			System.out.println("exception message:" + e.getMessage());
		} catch (SalesException s) {
			System.out.println("exception message:" + s.getMessage());
		}
	}
}
