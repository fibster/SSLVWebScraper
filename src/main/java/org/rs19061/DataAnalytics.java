package org.rs19061;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataAnalytics {

    public static int calculateAveragePrice(List<String[]> properties) {
        double sum = 0.0;
        int count = 0;

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

        // Round the average price to the nearest whole number
        return (int) Math.round(average);
    }

    public static int getMinimumPrice(List<String[]> properties) {
        int minPrice = Integer.MAX_VALUE;

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

        return minPrice == Integer.MAX_VALUE ? -1 : minPrice;
    }

    public static int getMaximumPrice(List<String[]> properties) {
        int maxPrice = Integer.MIN_VALUE;

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

        return maxPrice == Integer.MIN_VALUE ? -1 : maxPrice;
    }

    public static Map<String, Integer> countPropertyTypes(List<String[]> properties) {
        Map<String, Integer> propertyTypeCount = new HashMap<>();

        for (String[] property : properties) {
            if (property.length > 4) {
                String propertyType = property[4]; // change index as per your data structure
                propertyTypeCount.put(propertyType, propertyTypeCount.getOrDefault(propertyType, 0) + 1);
            }
        }

        return propertyTypeCount;
    }

}
