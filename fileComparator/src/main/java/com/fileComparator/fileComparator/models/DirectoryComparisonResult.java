package com.fileComparator.fileComparator.models;

import java.util.List;

public class DirectoryComparisonResult {
    private List<String> directory1Contents;
    private List<String> directory2Contents;
    private int totalFoldersInBoth;
    private int foldersNotPresentInDirectory1;
    private int foldersNotPresentInDirectory2;
    
    
	public List<String> getDirectory1Contents() {
		return directory1Contents;
	}
	public void setDirectory1Contents(List<String> directory1Contents) {
		this.directory1Contents = directory1Contents;
	}
	public List<String> getDirectory2Contents() {
		return directory2Contents;
	}
	public void setDirectory2Contents(List<String> directory2Contents) {
		this.directory2Contents = directory2Contents;
	}
	public int getTotalFoldersInBoth() {
		return totalFoldersInBoth;
	}
	public void setTotalFoldersInBoth(int totalFoldersInBoth) {
		this.totalFoldersInBoth = totalFoldersInBoth;
	}
	public int getFoldersNotPresentInDirectory1() {
		return foldersNotPresentInDirectory1;
	}
	public void setFoldersNotPresentInDirectory1(int foldersNotPresentInDirectory1) {
		this.foldersNotPresentInDirectory1 = foldersNotPresentInDirectory1;
	}
	public int getFoldersNotPresentInDirectory2() {
		return foldersNotPresentInDirectory2;
	}
	public void setFoldersNotPresentInDirectory2(int foldersNotPresentInDirectory2) {
		this.foldersNotPresentInDirectory2 = foldersNotPresentInDirectory2;
	}

    
    
    
}

