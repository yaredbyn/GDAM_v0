package com.gdam.controller;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gdam.models.Donor;

@Controller
public class MainController {
	
	@GetMapping("")
	public String viewHomePage() {
		return "Home";
	}
	
	@PostMapping("/add")
	@ResponseBody
	public String addDonor(Donor donor, RedirectAttributes redirectAttributes) {
	    // Define base file path
	    String baseFilePath = "donor_details";
	    String fileExtension = ".txt";
	    int maxFileSizeInBytes = 1 * 1024 * 1024; // 1 MB in bytes
	    String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	    String donorDetails = String.format("firstName='%s', lastName='%s', email='%s', amountDonated=%.1f",
	        donor.getFirstName(), donor.getLastName(), donor.getEmail(), donor.getAmountDonated());
	    String output = String.format("[%s], %s%n", timestamp, donorDetails);

	    // Determine the current file to use
	    File file = getCurrentFile(baseFilePath, fileExtension, maxFileSizeInBytes);
	    
	    // Print to console
	    System.out.println(output);

	    // Write to file
	    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
	        writer.write(output);
	    } catch (IOException e) {
	        System.err.println("Error writing to file: " + e.getMessage());
	    }

	    // Add success message
	    redirectAttributes.addFlashAttribute("message", "Donor details received successfully.");

	    // Redirect to Home page
	    return "Successful";
	}

	private File getCurrentFile(String baseFilePath, String fileExtension, int maxFileSizeInBytes) {
	    int fileIndex = 1;
	    File file;
	    String filePath;

	    do {
	        filePath = String.format("%s_%d%s", baseFilePath, fileIndex, fileExtension);
	        file = new File(filePath);
	        fileIndex++;
	    } while (file.exists() && file.length() >= maxFileSizeInBytes);

	    return file;
	}

}

