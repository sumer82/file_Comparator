package com.fileComparator.fileComparator.models;

import java.util.List;

public class FileComparisonResult {
	
	 private String fileName;
	 private List<String> differences;
	
	 
	 
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public List<String> getDifferences() {
		return differences;
	}
	public void setDifferences(List<String> differences) {
		this.differences = differences;
	}
	
	
	 
	 

}
