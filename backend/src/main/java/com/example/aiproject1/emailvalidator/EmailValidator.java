package com.example.aiproject1.emailvalidator;

public class EmailValidator {

    /**
     * Stricter email validation.
     * Enforces:
     *  - Allowed characters only
     *  - No consecutive dots
     *  - Cannot start/end with dot in local or domain part
     *  - Domain requires at least one dot
     *  - Top-level domain 2–24 letters
     */
    public boolean isCorrectEmail(String email) {
        if (email == null) return false;

        // Reject consecutive dots anywhere
        if (email.contains("..")) return false;

        // Stricter regex for email validation
        String pattern =
                "^(?=.{1,254}$)" +                        // Entire email max length
                        "(?=.{1,64}@)" +                          // Local part max length
                        "[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+" +       // Local part start
                        "(?:\\.[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+)*" +
                        "@" +
                        "(?:[A-Za-z0-9](?:[A-Za-z0-9-]{0,61}[A-Za-z0-9])?\\.)+" + // Domain labels
                        "[A-Za-z]{2,24}$";                        // TLD

        return email.matches(pattern);
    }


}
