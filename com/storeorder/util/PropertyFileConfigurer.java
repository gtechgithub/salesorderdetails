package com.storeorder.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application.properties")
@ConfigurationProperties("sales")
public class PropertyFileConfigurer {

	private String csvfileNamepath;
	private String duplicateEntryfile;
	private int batchSize;
	
	public String getCsvfileNamepath() {
		return csvfileNamepath;
	}
	public void setCsvfileNamepath(String csvfileNamepath) {
		this.csvfileNamepath = csvfileNamepath;
	}
	public String getDuplicateEntryfile() {
		return duplicateEntryfile;
	}
	public void setDuplicateEntryfile(String duplicateEntryfile) {
		this.duplicateEntryfile = duplicateEntryfile;
	}
	public int getBatchSize() {
		return batchSize;
	}
	public void setBatchSize(int batchSize) {
		this.batchSize = batchSize;
	}
}
