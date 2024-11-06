public class InputHandler {

    // Method to replace placeholders with hostname and IP address
    public static String replacePlaceholders(String template, String hostname, String ipAddress) {
        if (hostname == null || hostname.isEmpty()) {
            hostname = "No Hostname";
        }
        if (ipAddress == null || ipAddress.isEmpty()) {
            ipAddress = "No IP Address";
        }
        return template.replace("{hostname}", hostname).replace("{ip}", ipAddress);
    }

    public static void main(String[] args) {
        // Sample template
        String template = "Hostname: {hostname}, IP Address: {ip}";

        // Test Cases
        testReplacePlaceholders("Test 1 - Valid Hostname and IP Address", template, "localhost", "192.168.1.1");
        testReplacePlaceholders("Test 2 - Empty Hostname", template, "", "192.168.1.1");
        testReplacePlaceholders("Test 3 - Empty IP Address", template, "localhost", "");
    }

    // Test method to run test cases
    private static void testReplacePlaceholders(String testName, String template, String hostname, String ipAddress) {
        System.out.println(testName);
        System.out.println("Input: Hostname = " + hostname + ", IP Address = " + ipAddress);
        String result = replacePlaceholders(template, hostname, ipAddress);
        System.out.println("Output: " + result);
        System.out.println("------------------------");
    }
}
