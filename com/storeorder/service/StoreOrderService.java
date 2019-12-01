package com.storeorder.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.storeorder.entity.StoreOrder;
import com.storeorder.exception.SalesException;
import com.storeorder.models.StoreOrderDetails;
import com.storeorder.repository.StoreOrderRepository;
import com.storeorder.util.PropertyFileConfigurer;
import com.storeorder.util.csvStoreOrderReader;

@Service
public class StoreOrderService {

	@Autowired
	StoreOrderRepository storeRepo;

	@Autowired
	csvStoreOrderReader csvSalesReader;

	@Autowired
	PropertyFileConfigurer propertyConfig;

	private List<String> orderIds;
	private List<String> productIds;
	private List<String> customerIds;
	List<StoreOrderDetails> duplicateRecordIdList;

	public StoreOrderService() {
		orderIds = new ArrayList<>();
		productIds = new ArrayList<>();
		customerIds = new ArrayList<>();
		duplicateRecordIdList = new ArrayList<>();
	}

	@Transactional(readOnly = true)
	public void LoadStoreOrderService() {

		// load from table by unique records of Order id, product id and customer id
		orderIds = storeRepo.findAllUniqueOrderIds();
		// load from table by unique records of Order id, product id and customer id
		productIds = storeRepo.findAllUniqueProductIds();

		// load from table by unique records of Order id, product id and customer id
		customerIds = storeRepo.findAllUniqueCustomerIds();

	}

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

			if (batchCounter.incrementAndGet() % propertyConfig.getBatchSize() == 0
					|| (batchCounter.get() == storeOrd.size())) {
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

		try {
			List<StoreOrder> storeOrderList = new ArrayList<>();

			csvSalesReader.parseCSV();
			List<StoreOrderDetails> salesDetails = csvSalesReader.getSalesDetailList();

			// load existing records from table
			LoadStoreOrderService();
			csvSalesReader.parseCSV();

			// include and form unique records
			Optional.ofNullable(salesDetails).ifPresent(
					salesDetail -> salesDetail.forEach(salesDetai -> formUniqueRecords(storeOrderList, salesDetai)));

			// bulk insert logic
			saveAllRecords(storeOrderList);

			// record the file
			fileToLogDuplicateRecords(salesDetails, storeOrderList);

		} catch (FileNotFoundException e) {
			System.out.println("exception message:" + e.getMessage());
		} catch (IOException e) {
			System.out.println("exception message:" + e.getMessage());
		} catch (SalesException s) {
			System.out.println("exception message:" + s.getMessage());
		}
	}

	private boolean isUniqueRecordSet(StoreOrderDetails salesDetail) {
		if (CollectionUtils.isEmpty(orderIds) && CollectionUtils.isEmpty(productIds)
				&& CollectionUtils.isEmpty(customerIds)) {
			return true;
		} else if ((!orderIds.contains(salesDetail.getOrderId())) && (!productIds.contains(salesDetail.getProductId()))
				&& (!customerIds.contains(salesDetail.getCustomerId()))) {
			return true;
		}
		return false;
	}

	private void writeToFile(StoreOrderDetails storeorder, StringBuilder content) {

		content.append("duplicate or null record found for rowid:" + storeorder.getRowId());
		content.append(" Order id:" + storeorder.getOrderId());
		content.append(" Product id:" + storeorder.getProductId());
		content.append(" CustomerId id:" + storeorder.getCustomerId());
		content.append("\n");

	}

	private void fileToLogDuplicateRecords(List<StoreOrderDetails> salesDetailsList, List<StoreOrder> storeOrderList)
			throws IOException {

		Path path = Paths.get(propertyConfig.getDuplicateEntryfile());
		StringBuilder content = new StringBuilder();
		if (!CollectionUtils.isEmpty(salesDetailsList)) {

			duplicateRecordIdList.stream().filter(Objects::nonNull).forEach(arg0 -> writeToFile(arg0, content));
		}

		try {
			Files.write(path, content.toString().getBytes());
		} catch (IOException e) {
			throw new SalesException("error while writing duplicate records in a file");
		}
	}

	private void formUniqueRecords(List<StoreOrder> storeOrderList, StoreOrderDetails salesDetail) {

		if (null != salesDetail && isUniqueRecordSet(salesDetail)) {

			ModelMapper modelMapper = new ModelMapper();
			StoreOrder storeOrderTemp = modelMapper.map(salesDetail, StoreOrder.class);
			storeOrderList.add(storeOrderTemp);

			if (null != storeOrderTemp.getOrderId()) {
				orderIds.add(storeOrderTemp.getOrderId());
			}

			if (null != storeOrderTemp.getProductId()) {
				productIds.add(storeOrderTemp.getProductId());
			}
			if (null != storeOrderTemp.getCustomerID()) {
				customerIds.add(storeOrderTemp.getCustomerID());
			}

		} else {
			duplicateRecordIdList.add(salesDetail);
		}
	}
}
