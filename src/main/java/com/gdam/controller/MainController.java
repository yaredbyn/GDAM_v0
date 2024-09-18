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
    private static int entryCount = 1; // Counter for entries

    private static final int WIDTH_FIRST_NAME = 15;
    private static final int WIDTH_LAST_NAME = 15;
    private static final int WIDTH_EMAIL = 20;
    private static final int WIDTH_AMOUNT = 15;
    private static final int WIDTH_DATE = 20;


	@GetMapping("")
	public String viewHomePage() {
		return "Home";
	}
	
	@RequestMapping(value = "/payment", method = RequestMethod.GET)
    public String showPaymentPage() {
        // This will return the payment.html page located in src/main/resources/templates
        return "payment";
    }



	    @PostMapping("/add")
	    @ResponseBody
	    public String addDonor(Donor donor, RedirectAttributes redirectAttributes) {
	        // Define the output headline
	        String headline = "This is under development";

	        // Prepare the output for the file
	        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	        String formattedDetails = String.format("%d | %-"+WIDTH_FIRST_NAME+"s | %-"+WIDTH_LAST_NAME+"s | %-"+WIDTH_EMAIL+"s | %-"+WIDTH_AMOUNT+".1f | %-"+WIDTH_DATE+"s",
	            entryCount++,
	            donor.getFirstName(),
	            donor.getLastName(),
	            donor.getEmail(),
	            donor.getAmountDonated(),
	            timestamp
	        );
	        String output = String.format("%s%n%s%n%s%n", headline, getHeader(), formattedDetails);

	        // Determine the current file to use
	        File file = getCurrentFile();

	        // Print to console
	     //   System.out.println(output);

	        // Write to file
	        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
	            // Write the headline and header only if the file is newly created
	            if (file.length() == 0) {
	                writer.write(output);
	            } else {
	                writer.write(formattedDetails);
	                writer.newLine(); // Add a newline after writing the output
	            }
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

	    private String getHeader() {
	        return String.format("No  | %-"+WIDTH_FIRST_NAME+"s | %-"+WIDTH_LAST_NAME+"s | %-"+WIDTH_EMAIL+"s | %-"+WIDTH_AMOUNT+"s | %-"+WIDTH_DATE+"s",
	            "First Name",
	            "Last Name",
	            "Email",
	            "Amount Donated",
	            "Date"
	        );
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
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error reading file: " + e.getMessage());
	        }

	        return ResponseEntity.ok()
	                .header(HttpHeaders.CONTENT_TYPE, "text/plain")
	                .body(fileContent.toString());
	    }
	}
