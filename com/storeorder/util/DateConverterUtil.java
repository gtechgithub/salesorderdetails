package com.storeorder.util;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.storeorder.constants.Constants;
import com.storeorder.exception.SalesException;


public interface DateConverterUtil {

	public static Date convertStringToDate(String dateString) {
		// convert string to date time
		LocalDate date = null;
		
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.ddmmyyydateFormatInCSV);
			date = LocalDate.parse(dateString, formatter);

		} catch (DateTimeParseException ex) {
			throw new SalesException("enter the proper date in "  + Constants.ddmmyyydateFormatInCSV +  " format");
		} catch (SalesException ex) {
			throw new SalesException("Recevied excepion during date parser " + ex.getMessage());
		}
		return Date.valueOf(date);
	}}

