package org.rs19061;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Fetcher {

    // Method to fetch cities from a URL
    public static List<String> fetchCities(String url) {
        List<String> cities = new ArrayList<>();

        try {
            // Connect to the specified URL and retrieve the HTML document
            Document doc = Jsoup.connect(url).get();
            // Select all h4 elements with class "category"
            Elements h4Elements = doc.select("h4.category");

            // Iterate over each h4 element
            for (Element h4 : h4Elements) {
                // Extract the text from the "a" element inside the h4 element, representing the city
                String city = h4.select("a").text();
                // Check if the city is not "Dzīvokļi ārpus Latvijas" or "Mājas ārpus Latvijas"
                if (!city.equals("Dzīvokļi ārpus Latvijas") && !city.equals("Mājas ārpus Latvijas")) {
                    // Add the city to the list
                    cities.add(city);
                }
            }
        } catch (IOException e) {
            // Print the stack trace if an IO exception occurs
            e.printStackTrace();
        }

        // Return the list of cities
        return cities;
    }
}