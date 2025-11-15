package com.example.aiproject1.emailvalidator;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin(origins = "http://localhost:4200") // Allow Angular frontend
public class EmailValidatorController {

    private final EmailValidator emailValidator = new EmailValidator();

    /**
     * Endpoint:
     * GET /validateEmail?email=value
     */
    @GetMapping("/validateEmail")
    public EmailValidationResponse validateEmail(@RequestParam String email) {
        boolean isValid = emailValidator.isCorrectEmail(email);
        return new EmailValidationResponse(email, isValid);
    }

    public static class EmailValidationResponse {
        private String email;
        private boolean valid;

        public EmailValidationResponse(String email, boolean valid) {
            this.email = email;
            this.valid = valid;
        }

        public String getEmail() {
            return email;
        }

        public boolean isValid() {
            return valid;
        }
    }

}
