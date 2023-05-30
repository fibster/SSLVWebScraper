package org.rs19061;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class FlatFetcher extends BasePropertyFetcher {
    public FlatFetcher() {
        super(row -> {
            Elements tdElements = row.select("td");

            if (tdElements.size() >= 8) {
                String city = tdElements.get(3).text();
                String rooms = tdElements.get(4).text();
                String area = tdElements.get(5).text();
                String floor = tdElements.get(6).text().split("/")[0];
                String projectType = tdElements.get(7).text();
                String price = tdElements.get(8).text().replace(" €", "").replace(",", "");

                if (price.contains("pērku") || price.contains("vēlos īret") || price.contains("/ned.")||
                        price.contains("/mēn.") || price.contains("/dienā") || price.contains("maiņai") ||
                        price.contains("-")){
                    return null;
                }

                if (area.contains("ha.")) {
                    area = area.replace(" ha.", ""); // Remove the " ha" suffix
                    double areaInHectares = Float.parseFloat(area);
                    double areaInSquareMeters = areaInHectares * 10000; // Multiply by 10,000
                    int areaInt = (int) Math.round(areaInSquareMeters); // Convert to int
                    area = Integer.toString(areaInt); // Convert back to string
                }

                return new String[]{price, rooms, area, floor, projectType, city};
            }

            return null;
        });
    }
}