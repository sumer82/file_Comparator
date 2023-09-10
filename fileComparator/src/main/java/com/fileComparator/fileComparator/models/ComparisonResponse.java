package com.fileComparator.fileComparator.models;

import java.util.List;

public class ComparisonResponse {
    private List<FileComparisonResult> results;
    private int totalFilesInBoth;

    public ComparisonResponse(List<FileComparisonResult> results, int totalFilesInBoth) {
        this.results = results;
        this.totalFilesInBoth = totalFilesInBoth;
    }

    public List<FileComparisonResult> getResults() {
        return results;
    }

    public void setResults(List<FileComparisonResult> results) {
        this.results = results;
    }

    public int getTotalFilesInBoth() {
        return totalFilesInBoth;
    }

    public void setTotalFilesInBoth(int totalFilesInBoth) {
        this.totalFilesInBoth = totalFilesInBoth;
    }
}
