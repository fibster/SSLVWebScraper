package org.rs19061;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class CSVWriter {

    // Method to write data to a CSV file
    public void writeDataToCSV(List<String[]> data, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(fileName), StandardCharsets.UTF_8))) {

            // Iterate over each record in the data list
            for (String[] record : data) {
                // Convert the record array to a string with "|" separator and write it to the file
                writer.write(String.join("|", record));
                // Write a new line after each record
                writer.newLine();
            }

            // Print a success message after writing the data
            System.out.println("Data has been written to " + fileName);
        } catch (IOException e) {
            // Handle any IO exceptions that occur during the writing process
            System.out.println("Error writing data to CSV: " + e.getMessage());
        }
    }
}