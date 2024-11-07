import java.util.regex.Pattern;

public class NetworkValidator {

    // Method to validate hostname with special characters
    public static boolean isValidHostname(String hostname) {
        // Hostname regex allowing letters, digits, hyphens, and dots, with length up to 253 characters
        String hostnameRegex = "^[a-zA-Z0-9.-]{1,253}$";
        return Pattern.matches(hostnameRegex, hostname) && hostname.length() <= 253;
    }

    // Method to validate IP address with leading zeros
    public static boolean isValidIPAddress(String ip) {
        // IP regex allowing numbers only within 0-255 range, ignoring leading zeros
        String ipRegex = "^((25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9]?[0-9])\\.){3}(25[0-5]|2[0-4][0-9]|[1-9]?[0-9])$";
        return Pattern.matches(ipRegex, ip);
    }

    // Main method to test the validation with edge cases
    public static void main(String[] args) {
        // Test cases
        String hostnameWithSpecialChars = "host@name.com";
        String longHostname = "a".repeat(254); // Exceeds 253-character limit
        String validHostname = "valid-hostname.com";

        String ipWithLeadingZeros = "192.168.001.001";
        String validIP = "192.168.1.1";
        String invalidIP = "256.300.1.1"; // Out of range IP

        // Output results
        System.out.println("Testing Hostname with Special Characters:");
        System.out.println("Hostname: " + hostnameWithSpecialChars + " - Valid: " + isValidHostname(hostnameWithSpecialChars));

        System.out.println("\nTesting Long Hostname:");
        System.out.println("Hostname: " + longHostname + " - Valid: " + isValidHostname(longHostname));

        System.out.println("\nTesting Valid Hostname:");
        System.out.println("Hostname: " + validHostname + " - Valid: " + isValidHostname(validHostname));

        System.out.println("\nTesting IP Address with Leading Zeros:");
        System.out.println("IP Address: " + ipWithLeadingZeros + " - Valid: " + isValidIPAddress(ipWithLeadingZeros));

        System.out.println("\nTesting Valid IP Address:");
        System.out.println("IP Address: " + validIP + " - Valid: " + isValidIPAddress(validIP));

        System.out.println("\nTesting Invalid IP Address (Out of Range):");
        System.out.println("IP Address: " + invalidIP + " - Valid: " + isValidIPAddress(invalidIP));
    }
}
