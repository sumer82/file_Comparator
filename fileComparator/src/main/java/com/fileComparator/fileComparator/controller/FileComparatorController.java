package com.fileComparator.fileComparator.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fileComparator.fileComparator.models.ComparisonResponse;
import com.fileComparator.fileComparator.models.DirectoryComparisonResult;
import com.fileComparator.fileComparator.models.FileComparisonResult;
import com.fileComparator.fileComparator.services.FileComparisonService;

@RestController
@RequestMapping("/file-comparison")
public class FileComparatorController {

	@Autowired
    private FileComparisonService fileComparisonService;

    @PostMapping("/compare-xls")
    public ResponseEntity<String> compareXlsFiles(
            @RequestParam("folderPath1") String folderPath1,
            @RequestParam("folderPath2") String folderPath2) {
        if (folderPath1 == null || folderPath2 == null) {
		    return ResponseEntity.badRequest().body("Both folder paths are required.");
		}

		String report = fileComparisonService.compareFolders1(folderPath1, folderPath2);
		return ResponseEntity.ok(report);
    }
    
    
    @PostMapping("/compare")
    public DirectoryComparisonResult compareDirectories(
            @RequestParam("path1") String path1,
            @RequestParam("path2") String path2) {
        // Create a result object to store comparison results
        DirectoryComparisonResult result = new DirectoryComparisonResult();

        // Get the list of files and folders in each directory
        List<String> directory1Contents = listFilesAndFolders(path1);
        List<String> directory2Contents = listFilesAndFolders(path2);

        // Compare the directories and populate the result object
        result.setDirectory1Contents(directory1Contents);
        result.setDirectory2Contents(directory2Contents);

        List<String> commonItems = new ArrayList<>(directory1Contents);
        commonItems.retainAll(directory2Contents);

        result.setTotalFoldersInBoth(commonItems.size());
        result.setFoldersNotPresentInDirectory1(directory2Contents.size() - commonItems.size());
        result.setFoldersNotPresentInDirectory2(directory1Contents.size() - commonItems.size());

        return result;
    }

    private List<String> listFilesAndFolders(String path) {
        File directory = new File(path);
        if (directory.exists() && directory.isDirectory()) {
            File[] filesAndFolders = directory.listFiles();
            if (filesAndFolders != null) {
                List<String> names = new ArrayList<>();
                for (File fileOrFolder : filesAndFolders) {
                    names.add(fileOrFolder.getName());
                }
                return names;
            }
        }
        return new ArrayList<>();
    }
    
    
    
    @PostMapping("/compare_file")
    public ResponseEntity<ComparisonResponse> compareFilesInFolders( @RequestParam("path1") String folderPath1,
            @RequestParam("path2") String folderPath2) {
        try {
            List<FileComparisonResult> results = fileComparisonService.compareFolders(folderPath1, folderPath2);
            int totalFilesInBoth = getTotalFiles(folderPath1, folderPath2);
            
            ComparisonResponse response = new ComparisonResponse(results, totalFilesInBoth);
            
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // Helper method to get the list of file names in a directory
    private List<String> getFileNames(String directoryPath) {
        File directory = new File(directoryPath);
        File[] files = directory.listFiles();
        List<String> fileNames = new ArrayList<>();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    fileNames.add(file.getName());
                }
            }
        }

        return fileNames;
    }
    
 // Helper method to get the total number of files in both directories
    private int getTotalFiles(String path1, String path2) {
        List<String> filesInPath1 = getFileNames(path1);
        List<String> filesInPath2 = getFileNames(path2);

        return filesInPath1.size() + filesInPath2.size();
    }
}


