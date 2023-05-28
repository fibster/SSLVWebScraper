package org.rs19061;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HouseFetcher implements PropertyFetcher {
    @Override
    public List<String[]> fetchProperties(String baseUrl, int maxPages) {
        List<String[]> data = new ArrayList<>();

        for (int pageNumber = 1; pageNumber <= maxPages; pageNumber++) {
            try {
                String url = baseUrl + "page" + pageNumber + ".html";
                Document doc = Jsoup.connect(url).get();
                Elements rows = doc.select("tr[id^=tr_]");

                for (Element row : rows) {
                    Elements tdElements = row.select("td");

                    if (tdElements.size() >= 5) {
                        String city = tdElements.get(3).text();
                        String areaHouse = tdElements.get(4).text();
                        String area = tdElements.get(6).text().replace(" m²", "");
                        String rooms = tdElements.get(5).text();
                        String price = tdElements.get(7).text().replace(" €", "").replace(" maiņai", "").replace(",","");

                        if (price.equalsIgnoreCase("pērku") || price.equalsIgnoreCase("vēlos īret") || price.contains("/mēn.") || price.contains("/dienā")  ) {
                            continue;
                        }

                        if (area.contains("ha.")) {
                            area = area.replace(" ha.", ""); // Remove the " ha" suffix
                            double areaInHectares = Float.parseFloat(area);
                            double areaInSquareMeters = areaInHectares * 10000; // Multiply by 10,000
                            int areaInt = (int) Math.round(areaInSquareMeters); // Convert to int
                            area = Integer.toString(areaInt); // Convert back to string
                        }


                        data.add(new String[]{city, areaHouse, area, rooms, price});
                    }

                }
            } catch (IOException e) {
                System.out.println("Error fetching houses data: " + e.getMessage());
            }
        }

        return data;
    }
}