package org.rs19061;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataAnalytics {

    // Method to calculate the average price from the list of properties
    public static int calculateAveragePrice(List<String[]> properties) {
        double sum = 0.0;
        int count = 0;

        // Iterate over each property in the list
        for (String[] property : properties) {
            if (property.length > 0) { // Check if price exists in property data
                String priceStr = property[0];
                try {
                    double price = Double.parseDouble(priceStr);
                    sum += price;
                    count++;
                } catch (NumberFormatException e) {
                    System.out.println("Unable to parse price: " + priceStr);
                }
            }
        }

        double average = count > 0 ? sum / count : 0;

        // Round the average price to the nearest whole number and return it
        return (int) Math.round(average);
    }

    // Method to get the minimum price from the list of properties
    public static int getMinimumPrice(List<String[]> properties) {
        int minPrice = Integer.MAX_VALUE;

        // Iterate over each property in the list
        for (String[] property : properties) {
            if (property.length > 0) {
                try {
                    int price = Integer.parseInt(property[0]);
                    if (price < minPrice) {
                        minPrice = price;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Unable to parse price: " + property[0]);
                }
            }
        }

        // If no valid minimum price is found, return -1; otherwise, return the minimum price
        return minPrice == Integer.MAX_VALUE ? -1 : minPrice;
    }

    // Method to get the maximum price from the list of properties
    public static int getMaximumPrice(List<String[]> properties) {
        int maxPrice = Integer.MIN_VALUE;

        // Iterate over each property in the list
        for (String[] property : properties) {
            if (property.length > 0) {
                try {
                    int price = Integer.parseInt(property[0]);
                    if (price > maxPrice) {
                        maxPrice = price;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Unable to parse price: " + property[0]);
                }
            }
        }

        // If no valid maximum price is found, return -1; otherwise, return the maximum price
        return maxPrice == Integer.MIN_VALUE ? -1 : maxPrice;
    }

    // Method to count the occurrences of each property type in the list of properties
    public static Map<String, Integer> countPropertyTypes(List<String[]> properties) {
        Map<String, Integer> propertyTypeCount = new HashMap<>();

        // Iterate over each property in the list
        for (String[] property : properties) {
            if (property.length > 4) {
                String propertyType = property[4]; // change index as per your data structure
                // Increment the count for the property type in the map
                propertyTypeCount.put(propertyType, propertyTypeCount.getOrDefault(propertyType, 0) + 1);
            }
        }

        // Return the map containing the count of each property type
        return propertyTypeCount;
    }

}