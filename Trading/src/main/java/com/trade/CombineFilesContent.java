package com.trade;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

public class CombineFilesContent {

    public static void main(String[] args) {
        String directoryPath = "D:\\GitHub-Projects\\InterviewPrograms\\Trading\\src\\main\\java"; // Replace with your directory path
        combineAndPrintFilesContent(directoryPath);
    }

    private static void combineAndPrintFilesContent(String directoryPath) {
        File directory = new File(directoryPath);

        if (!directory.isDirectory()) {
            System.out.println("Invalid directory path");
            return;
        }

        processFiles(directory);
    }

    private static void processFiles(File directory) {
        File[] files = directory.listFiles();

        if (files == null || files.length == 0) {
            System.out.println("No files in the directory");
            return;
        }

        Arrays.sort(files); // Optional: Sort files alphabetically

        for (File file : files) {
            if (file.isFile()) {
                try {
                    String fileContent = new String(Files.readAllBytes(file.toPath()));

                    // Remove newlines and make the text compact
                    fileContent = fileContent.replaceAll("\\s+", " ");

                    System.out.println("File: " + file.getName());
                    System.out.println(fileContent);
                    System.out.println(); // Add a line space between files
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (file.isDirectory()) {
                processFiles(file); // Recursively process files in subdirectories
            }
        }
    }
}
