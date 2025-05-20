package com.medcon.shared.constants;

/**
 * Application constants class containing shared configuration values.
 */
public class AppConstants {

    /**
     * Frontend URL for CORS configuration and other frontend-related operations.
     */
    public static final String FRONTEND_URL = "http://localhost:4200";

    // Private constructor to prevent instantiation of utility class
    private AppConstants() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
