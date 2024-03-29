package com.storeorder.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.storeorder.models.StoreOrderDetails;

@Component
public class csvStoreOrderReader {

	private List<StoreOrderDetails> salesDetailList;

	@Autowired
	PropertyFileConfigurer propertyConfig;

	public List<StoreOrderDetails> getSalesDetailList() {
		return (null == salesDetailList ? Collections.emptyList() : salesDetailList);
	}

	public void parseCSV() throws FileNotFoundException, IOException {
		salesDetailList = new ArrayList<StoreOrderDetails>();

		// read from classpath resources
		// File file = File(getClass().getClassLoader().getResource(csvFile).getFile());

		Path path = Paths.get(propertyConfig.getCsvfileNamepath());

		CSVParser parser = CSVParser.parse(path, Charset.defaultCharset(), CSVFormat.DEFAULT.withHeader());
		parser.forEach(salesInformation -> populateSalesDetail(salesInformation));
		parser.close();
	}

	private void populateSalesDetail(CSVRecord csvRecord) {
		StoreOrderDetails storeOrderDetails = new StoreOrderDetails();

		//populate details from csvrecord
		storeOrderDetails.setRowId(Integer.parseInt(csvRecord.get("Row ID")));
		storeOrderDetails.setOrderId(csvRecord.get("Order ID"));
		storeOrderDetails.setCategoty(csvRecord.get("Category"));
		storeOrderDetails.setSubCategory(csvRecord.get("Sub-Category"));
		storeOrderDetails.setCity(csvRecord.get("City"));
		storeOrderDetails.setCountry(csvRecord.get("Country"));
		storeOrderDetails.setState(csvRecord.get("State"));
		storeOrderDetails.setSegment(csvRecord.get("Segment"));
		storeOrderDetails.setPostalCode(csvRecord.get("Postal Code"));
		storeOrderDetails.setRegion(csvRecord.get("Region"));
		storeOrderDetails.setCustomerId(csvRecord.get("Customer ID"));
		storeOrderDetails.setCustomerName(csvRecord.get("Customer Name"));
		storeOrderDetails.setDiscount(new BigDecimal(csvRecord.get("Discount")));
		storeOrderDetails.setProfit(new BigDecimal(csvRecord.get("Profit")));
		storeOrderDetails.setOrderDate(DateConverterUtil.convertStringToDate(csvRecord.get("Order Date")));
		storeOrderDetails.setShipDate(DateConverterUtil.convertStringToDate(csvRecord.get("Ship Date")));
		storeOrderDetails.setShipMode(csvRecord.get("Ship Mode"));
		storeOrderDetails.setProductId(csvRecord.get("Product ID"));
		storeOrderDetails.setProductName(csvRecord.get("Product Name"));
		storeOrderDetails.setSales(new BigDecimal(csvRecord.get("Sales")));
		storeOrderDetails.setSales(new BigDecimal(csvRecord.get("Sales")));
		storeOrderDetails.setQuantity(Integer.parseInt(csvRecord.get("Quantity")));

		salesDetailList.add(storeOrderDetails);
	}
}
