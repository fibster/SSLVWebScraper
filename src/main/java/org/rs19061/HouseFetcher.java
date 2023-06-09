package org.rs19061;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HouseFetcher extends BasePropertyFetcher {
    public HouseFetcher() {
        super(row -> {
            Elements tdElements = row.select("td");

            if (tdElements.size() >= 5) {
                // Extracting data from HTML table cells
                String city = tdElements.get(3).text();
                String areaHouse = tdElements.get(4).text();
                String area = tdElements.get(6).text().replace(" m²", "");
                String rooms = tdElements.get(5).text();
                String price = tdElements.get(7).text().replace(" €", "").replace(",","");

                // Filtering out unwanted data based on specific conditions
                if (price.contains("pērku") || price.contains("vēlos īret") || price.contains("/ned.") ||
                        price.contains("/mēn.") || price.contains("/dienā") || price.contains("maiņai") ||
                        price.contains("-")){
                    return null; // Skip this row if it contains unwanted data
                }

                // Converting area from hectares to square meters
                if (area.contains("ha.")) {
                    area = area.replace(" ha.", ""); // Remove the " ha" suffix
                    double areaInHectares = Float.parseFloat(area);
                    double areaInSquareMeters = areaInHectares * 10000; // Multiply by 10,000
                    int areaInt = (int) Math.round(areaInSquareMeters); // Convert to int
                    area = Integer.toString(areaInt); // Convert back to string
                }

                // Returning the extracted and processed data as an array
                return new String[]{price, areaHouse, area, rooms, city};
            }

            return null; // Skip this row if it doesn't have enough cells
        });
    }
}