package com.gdam.paypal;
import com.paypal.api.payments.*;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/paypal")
public class PayPalController {

    public PayPalController() {
		
	}

	@Autowired
    private PayPalService paypalService;

    @Value("${paypal.success.url}")
    private String successUrl;

    @Value("${paypal.cancel.url}")
    private String cancelUrl;

   
    @RequestMapping(value = "/pay", method = RequestMethod.GET)
    public String payment(RedirectAttributes redirectAttributes) {
        try {
            // Create a payment request to PayPal
            Payment payment = paypalService.createPayment(
                    0.01, // Amount             !!! only this should be provided by the user 
                    "USD", // Currency 
                    "paypal", // Payment Method 
                    "sale", // Payment Intent 
                    "Donation", // Description 
                    cancelUrl,
                    successUrl
            );
            
            // Print the payment object to debug
            System.out.println(payment.toString());

            // Extract and print the amount
            if (payment.getTransactions() != null && !payment.getTransactions().isEmpty()) {
                Transaction transaction = payment.getTransactions().get(0);
                Amount amount = transaction.getAmount();
                System.out.println("Amount provided to PayPal: " + amount.getTotal());
            } else {
                System.out.println("No transactions found in the payment response.");
            }

            // Redirect to PayPal for approval
            for (Links link : payment.getLinks()) {
                if (link.getRel().equals("approval_url")) {
                    return "redirect:" + link.getHref();
                }
            }
        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }


    @GetMapping("/success")
    public String successPayment(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved")) {
                return "redirect:/paypal/thank-you"; // Success page
            }
        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }

    @RequestMapping(value = "/cancel", method = RequestMethod.GET)
    public String cancelPayment() {
        return "redirect:/paypal/payment-cancelled"; // Redirect to another URL
    }

    @RequestMapping(value = "/payment-cancelled", method = RequestMethod.GET)
    public String paymentCancelled() {
        return "payment-cancelled"; // Render the "payment-cancelled.html" template
    }
    
    @RequestMapping(value = "/thank-you", method = RequestMethod.GET)
    public String thankYouPage() {
        return "thank-you"; // Render the "payment-cancelled.html" template
    }
    
    
}
