public class PlaceholderReplacer {
    private String template;

    public PlaceholderReplacer(String template) {
        this.template = template;
    }

    public String replacePlaceholders(String hostname, String ipAddress) {
        String result = template;
        if (hostname != null && !hostname.isEmpty()) {
            result = result.replace("{hostname}", hostname);
        }
        if (ipAddress != null && !ipAddress.isEmpty()) {
            result = result.replace("{ip}", ipAddress);
        }
        return result;
    }

    // Unit tests
    public static void main(String[] args) {
        PlaceholderReplacer replacer = new PlaceholderReplacer("Connecting to {hostname} at IP {ip}");

        // Test Case 1: Valid Hostname and IP Address
        String result1 = replacer.replacePlaceholders("exampleHost", "192.168.1.1");
        System.out.println("Test 1 - Valid Hostname and IP Address: " + result1);

        // Test Case 2: Empty Hostname
        String result2 = replacer.replacePlaceholders("", "192.168.1.1");
        System.out.println("Test 2 - Empty Hostname: " + result2);

        // Test Case 3: Empty IP Address
        String result3 = replacer.replacePlaceholders("exampleHost", "");
        System.out.println("Test 3 - Empty IP Address: " + result3);

        // Test Case 4: Both Empty Hostname and IP Address
        String result4 = replacer.replacePlaceholders("", "");
        System.out.println("Test 4 - Both Empty Hostname and IP Address: " + result4);
    }
}
