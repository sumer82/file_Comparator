package com.fileComparator.fileComparator.services;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.fileComparator.fileComparator.models.FileComparisonResult;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class FileComparisonService {

    public String compareFolders1(String folderPath1, String folderPath2) {
        List<String> differences = new ArrayList<>();

        List<String> subfolders1 = listSubfolders(folderPath1);
        List<String> subfolders2 = listSubfolders(folderPath2);

        if (!subfolders1.equals(subfolders2)) {
            differences.add("Subfolders in the two folders are not the same.");
        }

        StringBuilder report = new StringBuilder();
        report.append("Comparison report:\n");
        for (String difference : differences) {
            report.append(difference).append("\n");
        }

        return report.toString();
    }

    private List<String> listSubfolders(String folderPath) {
        List<String> subfolders = new ArrayList<>();
        File folder = new File(folderPath);

        if (folder.exists() && folder.isDirectory()) {
            File[] subfolderFiles = folder.listFiles(File::isDirectory);
            if (subfolderFiles != null) {
                for (File subfolderFile : subfolderFiles) {
                    subfolders.add(subfolderFile.getName());
                }
            }
        }

        return subfolders;
    }
    
    //compare excel file 
    
    public List<FileComparisonResult> compareFolders(String folderPath1, String folderPath2) throws IOException {
        List<FileComparisonResult> results = new ArrayList<>();

        File folder1 = new File(folderPath1);
        File folder2 = new File(folderPath2);

        File[] excelFiles1 = folder1.listFiles((dir, name) -> name.toLowerCase().endsWith(".xlsx"));
        File[] excelFiles2 = folder2.listFiles((dir, name) -> name.toLowerCase().endsWith(".xlsx"));
        

        if (excelFiles1 != null && excelFiles2 != null) {
            for (File file1 : excelFiles1) {
                for (File file2 : excelFiles2) {
                    if (file1.getName().equalsIgnoreCase(file2.getName())) {
                        // Files with the same name found, perform comparison
                        List<String> differences = compareExcelFiles(file1, file2);
                        if (!differences.isEmpty()) {
                            // Add the result to the list of differences
                            FileComparisonResult result = new FileComparisonResult();
                            result.setFileName(file1.getName());
                            result.setDifferences(differences);
                            results.add(result);
                        }
                    }
                }
            }
        }

        return results;
    }

    private List<String> compareExcelFiles(File file1, File file2) throws IOException {
        List<String> differences = new ArrayList<>();

        try (FileInputStream fis1 = new FileInputStream(file1);
             FileInputStream fis2 = new FileInputStream(file2);
             Workbook workbook1 = new XSSFWorkbook(fis1);
             Workbook workbook2 = new XSSFWorkbook(fis2)) {

            // Iterate through sheets
            for (int i = 0; i < workbook1.getNumberOfSheets(); i++) {
                Sheet sheet1 = workbook1.getSheetAt(i);
                Sheet sheet2 = workbook2.getSheetAt(i);

                // Iterate through rows
                Iterator<Row> iterator1 = sheet1.iterator();
                Iterator<Row> iterator2 = sheet2.iterator();

                while (iterator1.hasNext() && iterator2.hasNext()) {
                    Row row1 = iterator1.next();
                    Row row2 = iterator2.next();

                    // Iterate through cells
                    Iterator<Cell> cellIterator1 = row1.cellIterator();
                    Iterator<Cell> cellIterator2 = row2.cellIterator();

                    while (cellIterator1.hasNext() && cellIterator2.hasNext()) {
                        Cell cell1 = cellIterator1.next();
                        Cell cell2 = cellIterator2.next();

                        // Compare cell values (you may need to refine this logic)
                        if (!cell1.toString().equals(cell2.toString())) {
                            differences.add("Row " + (row1.getRowNum() + 1) + ", Column " + (cell1.getColumnIndex() + 1) +
                                    ": " + cell1.toString() + " != " + cell2.toString());
                        }
                    }
                }
            }
        }

        return differences;
    }
}

