package com.gdam.controller;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gdam.models.Donor;

@Controller
public class MainController {

	private static final String BASE_FILE_PATH = "donor_details";
	private static final String FILE_EXTENSION = ".txt";
	private static final int MAX_FILE_SIZE_IN_BYTES = 1 * 1024 * 1024; // 1 MB in bytes

	@GetMapping("")
	public String viewHomePage() {
		return "Home";
	}

	@PostMapping("/add")
	@ResponseBody
	public String addDonor(Donor donor, RedirectAttributes redirectAttributes) {
		// Generate output string
		String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		String donorDetails = String.format("firstName='%s', lastName='%s', email='%s', amountDonated=%.1f",
				donor.getFirstName(), donor.getLastName(), donor.getEmail(), donor.getAmountDonated());
		String output = String.format("[%s], %s%n", timestamp, donorDetails);

		// Determine the current file to use
		File file = getCurrentFile();

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

	private File getCurrentFile() {
		int fileIndex = 1;
		File file;
		String filePath;

		do {
			filePath = String.format("%s_%d%s", BASE_FILE_PATH, fileIndex, FILE_EXTENSION);
			file = new File(filePath);
			fileIndex++;
		} while (file.exists() && file.length() >= MAX_FILE_SIZE_IN_BYTES);

		return file;
	}

	@GetMapping("/view-file")
	public ResponseEntity<String> viewFile() {
		// Determine the most recent file to read
		File file = getCurrentFile();

		if (!file.exists()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found.");
		}

		StringBuilder fileContent = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = reader.readLine()) != null) {
				fileContent.append(line).append(System.lineSeparator());
			}
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error reading file: " + e.getMessage());
		}

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "text/plain").body(fileContent.toString());
	}
}
