import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileHandler {
    
    public boolean saveToFile(String content, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(content);
            System.out.println("Configuration saved successfully.");
            return true;
        } catch (IOException e) {
            System.err.println("Error saving to file: " + e.getMessage());
            return false;
        }
    }

    // Main method to run tests
    public static void main(String[] args) {
        FileHandler fileHandler = new FileHandler();
        String filePath = "config.txt";
        
        // Test Case 1: Successful File Save
        String configContent = "hostname=exampleHost\nip=192.168.1.1";
        System.out.println("Test 1 - Successful File Save:");
        boolean isSaved = fileHandler.saveToFile(configContent, filePath);
        System.out.println("Result: " + (isSaved ? "Pass" : "Fail"));

        // Test Case 2: File Open Error (simulate by making the file read-only)
        System.out.println("\nTest 2 - File Open Error:");
        File file = new File(filePath);
        file.setReadOnly(); // Simulate restricted write permissions
        boolean isSavedError = fileHandler.saveToFile(configContent, filePath);
        System.out.println("Result: " + (!isSavedError ? "Pass" : "Fail"));
        
        // Clean up by resetting file permissions if necessary
        file.setWritable(true);
    }
}
