package com.gdam.controller;

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
	    // Process the donor object
	    System.out.println(donor.toString());
	    // Add success message
	    redirectAttributes.addFlashAttribute("message", "Donor details received successfully.");
	    // Redirect to Home page
	    return "Successful";
	}
}
