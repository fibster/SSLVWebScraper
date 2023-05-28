package org.rs19061;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class CSVWriter {

    public void writeDataToCSV(List<String[]> data, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(fileName), StandardCharsets.UTF_8))) {

            for (String[] record : data) {
                writer.write(String.join("|", record));
                writer.newLine();
            }

            System.out.println("Data has been written to " + fileName);
        } catch (IOException e) {
            System.out.println("Error writing data to CSV: " + e.getMessage());
        }
    }
}