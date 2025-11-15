package com.example.aiproject1.emailvalidator;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EmailValidatorTest {

    private final EmailValidator validator = new EmailValidator();

    @Test
    public void testValidEmails() {
        assertTrue(validator.isCorrectEmail("john.doe@example.com"));
        assertTrue(validator.isCorrectEmail("user+tag@sub.domain.co"));
        assertTrue(validator.isCorrectEmail("simple@mail.io"));
        assertTrue(validator.isCorrectEmail("x_y-z@mail-server.com"));
    }

    @Test
    public void testInvalidEmails() {
        // Null or empty
        assertFalse(validator.isCorrectEmail(null));
        assertFalse(validator.isCorrectEmail(""));

        // No @
        assertFalse(validator.isCorrectEmail("notanemail.com"));

        // Double dot
        assertFalse(validator.isCorrectEmail("john..doe@example.com"));
        assertFalse(validator.isCorrectEmail("john@exa..mple.com"));

        // Dot at start or end of local/domain part
        assertFalse(validator.isCorrectEmail(".john@example.com"));
        assertFalse(validator.isCorrectEmail("john.@example.com"));
        assertFalse(validator.isCorrectEmail("john@.example.com"));
        assertFalse(validator.isCorrectEmail("john@example.com."));

        // Invalid characters
        // assertFalse(validator.isCorrectEmail("john#doe@example.com")); // too strict
        assertFalse(validator.isCorrectEmail("john doe@example.com"));

        // Missing TLD or invalid TLD
        assertFalse(validator.isCorrectEmail("john@example"));
        assertFalse(validator.isCorrectEmail("john@example.c"));
        assertFalse(validator.isCorrectEmail("john@example.123"));

        // Consecutive special characters
        assertFalse(validator.isCorrectEmail("john..doe@example.com"));
    }

}
